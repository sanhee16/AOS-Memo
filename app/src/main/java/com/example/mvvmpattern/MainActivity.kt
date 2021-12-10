package com.example.mvvmpattern

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mvvmpattern.R
import com.example.mvvmpattern.databinding.ActivityMainBinding
import com.example.mvvmpattern.view.DataShowActivity

class MainActivity : BaseActivity() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        b.btnMoveDataShow.text = "move to data show activity"
    }
    fun initDataBinding() {
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        b.main = this
    }
    fun moveDataShow() {
        startActivity(DataShowActivity::class.java)
    }
}