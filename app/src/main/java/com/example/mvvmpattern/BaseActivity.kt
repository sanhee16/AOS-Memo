package com.example.mvvmpattern

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmpattern.view.DataShowActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun startActivity(cls : Class<out Activity?>?) {
        val intent = Intent(this, cls)
        startActivity(intent)
//        finish()
    }
}