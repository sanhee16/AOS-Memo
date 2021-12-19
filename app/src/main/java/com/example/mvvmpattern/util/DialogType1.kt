package com.example.mvvmpattern.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.mvvmpattern.R
import com.example.mvvmpattern.databinding.DialogType1Binding

class DialogType1(context: Context) {
    private val b : DialogType1Binding = DataBindingUtil.setContentView(context as Activity, R.layout.dialog_type1)
    private val dialog = Dialog(context)
//    private lateinit var btnConfirm : TextView
//    private lateinit var btnCancel : TextView
    private lateinit var message: TextView

    fun start(message: String) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.dialog_type1)
        dialog.setCancelable(true)

//        this.message.text = message

        b.textMessage.text = message

        dialog.show()
    }

    fun onClickCancel() {
        Log.v("sandy","onClickCancel")
    }
}