package com.sandy.memo.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.databinding.DataBindingUtil
import com.sandy.memo.BaseActivity
import com.sandy.memo.R
import com.sandy.memo.databinding.ActivityCameraBinding
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.io.InputStream
import java.nio.ByteBuffer
import kotlin.math.max
import kotlin.math.sqrt

class CameraActivity : BaseActivity() {
    private lateinit var b: ActivityCameraBinding
    // https://www.charlezz.com/?p=45859

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

    private fun openCV() {
        val `is`: InputStream = assets.open("sample_image.jpg")
        val bitmap: Bitmap = BitmapFactory.decodeStream(`is`)
        val src: Mat = Mat()
        Utils.bitmapToMat(bitmap, src)
        b.image1.setImageBitmap(bitmap)

        // 흑백영상으로 전환
        val graySrc = Mat()
        Imgproc.cvtColor(src, graySrc, Imgproc.COLOR_BGR2GRAY)

        // 이진화
        val binarySrc = Mat()
        Imgproc.threshold(graySrc, binarySrc, 0.0, 255.0, Imgproc.THRESH_OTSU)
    }
}