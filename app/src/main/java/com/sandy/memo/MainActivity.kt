package com.sandy.memo

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sandy.memo.databinding.ActivityMainBinding
import com.sandy.memo.view.CameraActivity
import com.sandy.memo.view.DataShowActivity
import com.sandy.memo.view.MemoActivity

class MainActivity : BaseActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        b.btnMoveDataShow.text = "move to data show activity"
        b.btnMoveMemoActivity.text = "move to memo activity"
        b.btnMoveCameraActivity.text = "move to camera activity"
    }

    private fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        b.main = this
    }

    fun moveDataShow() {
        startActivity<DataShowActivity>()
    }

    fun moveMemoActivity() {
        startActivity<MemoActivity>()
    }

    fun moveCamera() {
        startActivity<CameraActivity>()
    }
}