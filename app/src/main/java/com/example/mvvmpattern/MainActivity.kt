package com.example.mvvmpattern

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.mvvmpattern.databinding.ActivityMainBinding
import com.example.mvvmpattern.view.DataShowActivity
import com.example.mvvmpattern.view.MemoActivity

class MainActivity : BaseActivity() {
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        b.btnMoveDataShow.text = "move to data show activity"
        b.btnMoveMemoActivity.text = "move to memo activity"
    }
    fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        b.main = this
    }
    fun moveDataShow() {
        startActivity<DataShowActivity>()
    }
    fun moveMemoActivity() {
        startActivity<MemoActivity>()
    }
}