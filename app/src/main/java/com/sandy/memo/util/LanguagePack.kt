package com.sandy.memo.util

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.nio.file.Paths
import javax.inject.Inject
import kotlin.io.path.createDirectory
import kotlin.io.path.exists


class LanguagePack @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val ENG = "eng.traineddata"
        private const val KOR = "kor.traineddata"

        @RequiresApi(Build.VERSION_CODES.O)
        fun copyFile(lang: String, context: Context) {
            try {
                //언어데이타파일의 위치
                val dataPath = context.filesDir.toString() + "/tessdata/" //언어데이터의 경로 미리 지정
                val dirPath = Paths.get(dataPath)
                val filePath = "$dataPath$lang.traineddata"
                if (!dirPath.exists()) {
                    Log.v("sandy", "No Dir : $dataPath")
                    File(dataPath).mkdirs()
                }
                if (!Paths.get(filePath).exists()) {
                    Log.v("sandy", "No File : $filePath")
                    //AssetManager를 사용하기 위한 객체 생성
                    //byte 스트림을 읽기 쓰기용으로 열기
                    val inputStream: InputStream = context.resources.assets.open("tessdata/$lang.traineddata")
                    val outStream: OutputStream = FileOutputStream(filePath)

                    //위에 적어둔 파일 경로쪽으로 해당 바이트코드 파일을 복사한다.
                    val buffer: ByteArray = ByteArray(1024)
                    var read: Int = 0
                    read = inputStream.read(buffer)
                    Log.v("sandy", "read : $read")
                    while (read != -1) {
                        outStream.write(buffer, 0, read)
                        read = inputStream.read(buffer)
                    }
                    outStream.flush()
                    outStream.close()
                    inputStream.close()
                }
            } catch (e: FileNotFoundException) {
                Log.v("오류발생", e.toString())
            } catch (e: IOException) {
                Log.v("오류발생", e.toString())
            }
        }
    }

}