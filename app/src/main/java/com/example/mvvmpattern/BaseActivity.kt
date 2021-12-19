package com.example.mvvmpattern

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T : Activity> Context.startActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    inline fun <reified T : Activity> Context.startActivityWithFinish() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
        finish()
    }

    inline fun <reified T : Activity> Context.startActivityWithFinish(bundle: Bundle) {
        val intent = Intent(this, T::class.java)
        intent.putExtra(Constants.BUNDLE, bundle)
        startActivity(intent)
        finish()
    }

    fun View.showProgress() {
        this.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun View.hideProgress() {
        this.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}