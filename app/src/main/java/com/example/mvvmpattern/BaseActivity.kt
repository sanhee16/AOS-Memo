package com.example.mvvmpattern

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inline fun <reified T: Activity> Context.startActivity(){
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
    inline fun <reified T: Activity> Context.startActivityWithFinish(){
        val intent = Intent(this, T::class.java)
        startActivity(intent)
        finish()
    }

    inline fun <reified T: Activity> Context.startActivityWithFinish(bundle: Bundle){
        val intent = Intent(this, T::class.java)
        intent.putExtra(Constants.BUNDLE, bundle)
        startActivity(intent)
        finish()
    }
}