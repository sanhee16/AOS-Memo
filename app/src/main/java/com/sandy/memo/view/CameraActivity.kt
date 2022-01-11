package com.sandy.memo.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.googlecode.tesseract.android.TessBaseAPI
import com.sandy.memo.BaseActivity
import com.sandy.memo.R
import com.sandy.memo.databinding.ActivityCameraBinding
import com.sandy.memo.util.LanguagePack
import kotlinx.coroutines.*
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.io.InputStream
import org.opencv.core.CvException

import org.opencv.core.Scalar

import org.opencv.core.CvType

import org.opencv.core.Mat
import kotlin.math.max
import kotlin.math.sqrt
import org.opencv.core.MatOfPoint
import java.io.File


class CameraActivity : BaseActivity() {
    private lateinit var b: ActivityCameraBinding
    // https://www.charlezz.com/?p=45859

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        val isIntialized = OpenCVLoader.initDebug()
        openCV()
    }

    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        b.lifecycleOwner = this
        b.act = this
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openCV() {
        val `is`: InputStream = assets.open("sample_image.jpg")
        val bitmap: Bitmap = BitmapFactory.decodeStream(`is`)
        val src = Mat()
        Utils.bitmapToMat(bitmap, src)
        b.imageOriginal.setImage(src)

        // 흑백영상으로 전환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)
        b.imageBlack.setImage(graySrc)

        // 이진화
        val binarySrc = Mat()
        Imgproc.threshold(graySrc, binarySrc, 0.0, 255.0, Imgproc.THRESH_OTSU)
        b.imageBinary.setImage(binarySrc)

        val approxCandidate = binarySrc.getContours()
        // 투시변환
        // 좌상단부터 시계 반대 방향으로 정점을 정렬한다.
        val points = arrayListOf(
            Point(approxCandidate.get(0, 0)[0], approxCandidate.get(0, 0)[1]),
            Point(approxCandidate.get(1, 0)[0], approxCandidate.get(1, 0)[1]),
            Point(approxCandidate.get(2, 0)[0], approxCandidate.get(2, 0)[1]),
            Point(approxCandidate.get(3, 0)[0], approxCandidate.get(3, 0)[1]),
        )
        points.sortBy { it.x } // x좌표 기준으로 먼저 정렬

        Log.v("sandy", "points : $points")
        if (points[0].y > points[1].y) {
            val temp = points[0]
            points[0] = points[1]
            points[1] = temp
        }

        if (points[2].y < points[3].y) {
            val temp = points[2]
            points[2] = points[3]
            points[3] = temp
        }
        // 원본 영상 내 정점들
        val srcQuad = MatOfPoint2f().apply { fromList(points) }
        Log.v("sandy", "srcQuad : $srcQuad")

        val maxSize = calculateMaxWidthHeight(
            tl = points[0],
            bl = points[1],
            br = points[2],
            tr = points[3]
        )
        val dw = maxSize.width
        val dh = dw * maxSize.height / maxSize.width
        val dstQuad = MatOfPoint2f(
            Point(0.0, 0.0),
            Point(0.0, dh),
            Point(dw, dh),
            Point(dw, 0.0)
        )
        Log.v("sandy", "dw : $dw")
        Log.v("sandy", "dh : $dh")
        Log.v("sandy", "dstQuad : $dstQuad")

        // 투시변환 매트릭스 구하기 = 원본 영상의 정점과 결과 영상에 적절히 대응되는 정점 입력을 통해 투시변환 매트릭스를 얻는다.
        val perspectiveTransform = Imgproc.getPerspectiveTransform(srcQuad, dstQuad)

        Log.v("sandy", "perspectiveTransform.height() : ${perspectiveTransform.height()}")
        Log.v("sandy", "perspectiveTransform.width() : ${perspectiveTransform.width()}")

        // 투시변환 된 결과 영상 얻기 = getPerspectiveTransform 호출로 얻은 매트릭스를 입력으로 하여 원본이미지를 변환
        val dst = Mat()
        Imgproc.warpPerspective(src, dst, perspectiveTransform, Size(dw, dh))
        b.imageResult.setImage(dst)
        var ocrResult: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            val job = CoroutineScope(Dispatchers.IO).launch {
                ocrResult = dst.printOCRResult(this@CameraActivity)
            }
            runBlocking {
                job.join()
                CoroutineScope(Dispatchers.Main).launch {
                    b.textContent.text = ocrResult
                }
            }
        }
    }

    private fun Mat.toBitmap(dst: ImageView): Bitmap? {
        var bmp: Bitmap? = null
        val tmp = Mat(dst.height, dst.width, CvType.CV_8U, Scalar(4.0))
        try {
            Imgproc.cvtColor(this, tmp, Imgproc.COLOR_GRAY2RGBA, 4)
            bmp = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(tmp, bmp)
        } catch (e: CvException) {
            e.message?.let { Log.d("Exception", it) }
        }
        return bmp
    }

    private fun ImageView.setImage(src: Mat) {
        val image = Mat(src.size(), CvType.CV_8UC1)
        val bmp = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(src, bmp)
        Glide.with(this@CameraActivity).load(bmp).into(this)
    }

    // 윤곽선 찾기
    private fun Mat.getContours(): MatOfPoint2f {
        val contours = ArrayList<MatOfPoint>()
        val hierarchy = Mat()
        Imgproc.findContours(
            this,
            contours,
            hierarchy,
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_NONE
        )
        // 하지만 윤곽선이 여러개 검출될 가능성이 높으므로, 원하는 영역의 윤곽선을 필터링 하기 위해 몇가지 조건들을 설정

        // 1. 가장 면적이 큰 윤곽선 찾기
        var biggestContour: MatOfPoint? = null
        var biggestContourArea: Double = 0.0
        for (contour in contours) {
            val area = Imgproc.contourArea(contour)
            if (area > biggestContourArea) {
                biggestContour = contour
                biggestContourArea = area
            }
        }

        if (biggestContour == null) {
            throw IllegalArgumentException("No Contour")
        }
//        // 1-1. 너무 작아도 안됨
        if (biggestContourArea < 400) {
            throw IllegalArgumentException("too small")
        }

        // 2. 근사화 하기
        val candidate2f = MatOfPoint2f(*biggestContour.toArray())
        val approxCandidate = MatOfPoint2f()
        Imgproc.approxPolyDP(
            candidate2f,
            approxCandidate,
            Imgproc.arcLength(candidate2f, true) * 0.02,
            true
        )

//         3.사각형인지 판별하기 (문서는 일반적으로 사각형이므로)
        if (approxCandidate.rows() != 4) {
            throw java.lang.IllegalArgumentException("It's not rectangle")
        }

//         컨벡스(볼록한 도형)인지 판별
        if (!Imgproc.isContourConvex(MatOfPoint(*approxCandidate.toArray()))) {
            throw java.lang.IllegalArgumentException("It's not convex")
        }

        Log.v("sandy", " approxCandidate : $approxCandidate")
        return approxCandidate
    }


    // 사각형 꼭짓점 정보로 사각형 최대 사이즈 구하기
    // 평면상 두 점 사이의 거리는 직각삼각형의 빗변길이 구하기와 동일
    private fun calculateMaxWidthHeight(
        tl: Point,
        tr: Point,
        br: Point,
        bl: Point,
    ): Size {
        // Calculate width
        val widthA = sqrt((tl.x - tr.x) * (tl.x - tr.x) + (tl.y - tr.y) * (tl.y - tr.y))
        val widthB = sqrt((bl.x - br.x) * (bl.x - br.x) + (bl.y - br.y) * (bl.y - br.y))
        val maxWidth = max(widthA, widthB)
        // Calculate height
        val heightA = sqrt((tl.x - bl.x) * (tl.x - bl.x) + (tl.y - bl.y) * (tl.y - bl.y))
        val heightB = sqrt((tr.x - br.x) * (tr.x - br.x) + (tr.y - br.y) * (tr.y - br.y))
        val maxHeight = max(heightA, heightB)
        return Size(maxWidth, maxHeight)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Mat.printOCRResult(context: Context): String {
        val image = Mat(this.size(), CvType.CV_8UC1)
        val bmp = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(this, bmp)
        LanguagePack.copyFile("eng", context)
        LanguagePack.copyFile("kor", context)
        val dataPath = context.filesDir.toString()

        // https://stackoverflow.com/questions/21161352/tess-two-ocr-not-working
        // 이미지 회전 처리하기!
        val tess = TessBaseAPI()
        tess.init(dataPath, "kor+eng")
        tess.setImage(bmp)
        Log.v("sandy", "result :\n ${tess.utF8Text}")
        return tess.utF8Text.toString()
    }
}
