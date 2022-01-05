package com.sandy.memo.util

import android.content.Context
import com.sandy.memo.R

import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.TextView


// https://developer.android.com/codelabs/advanced-android-kotlin-training-custom-views#3
class CreatePassword(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var btnConfirm: TextView
    private lateinit var btnCancel: TextView
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView
    private lateinit var text5: TextView
    private lateinit var text6: TextView
    private lateinit var text7: TextView
    private lateinit var text8: TextView
    private lateinit var text9: TextView
    private lateinit var text0: TextView
    private lateinit var textErase: TextView
    private lateinit var textReset: TextView
    private lateinit var textPassword: TextView
    private lateinit var textWarning: TextView

    private var password = "****"
    private var cnt = 0
    fun start() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.create_password)
        dialog.setCancelable(true)
        setView()
        setPasswordText()
        onClickNumbers()

        btnConfirm.setOnClickListener {
            if (cnt != 4) {
                textWarning.visibility = View.VISIBLE
            } else {
                listener.onConfirmClick(password)
            }
        }
        btnCancel.setOnClickListener {
            listener.onClickCancel()
        }
        dialog.show()
    }

    fun dismiss() = dialog.dismiss()
    interface OnClickListener {
        fun onConfirmClick(password: String)
        fun onClickCancel()
    }
    private lateinit var listener: OnClickListener
    fun setClickListener(listener: OnClickListener) {
        this.listener = listener
    }
    private fun onClickNumbers() {
        text1.setOnClickListener { replacePassword("1") }
        text2.setOnClickListener { replacePassword("2") }
        text3.setOnClickListener { replacePassword("3") }
        text4.setOnClickListener { replacePassword("4") }
        text5.setOnClickListener { replacePassword("5") }
        text6.setOnClickListener { replacePassword("6") }
        text7.setOnClickListener { replacePassword("7") }
        text8.setOnClickListener { replacePassword("8") }
        text9.setOnClickListener { replacePassword("9") }
        text0.setOnClickListener { replacePassword("0") }
        textErase.setOnClickListener {
            textWarning.visibility = View.INVISIBLE
            if ((cnt > 0) && (cnt < 5)) {
                password = password.replaceRange(cnt - 1, cnt, "*")
                cnt--
                setPasswordText()
            }
        }
        textReset.setOnClickListener {
            textWarning.visibility = View.INVISIBLE
            password = "****"
            cnt = 0
            setPasswordText()
        }
    }

    private fun replacePassword(num: String) {
        if (cnt < 4) {
            textWarning.visibility = View.INVISIBLE
            password = password.replaceRange(cnt, cnt + 1, num)
            cnt++
            setPasswordText()
        }
    }

    private fun setPasswordText() {
        textPassword.text = password
    }

    private fun setView() {
        this.text1 = dialog.findViewById(R.id.text_1)
        this.text2 = dialog.findViewById(R.id.text_2)
        this.text3 = dialog.findViewById(R.id.text_3)
        this.text4 = dialog.findViewById(R.id.text_4)
        this.text5 = dialog.findViewById(R.id.text_5)
        this.text6 = dialog.findViewById(R.id.text_6)
        this.text7 = dialog.findViewById(R.id.text_7)
        this.text8 = dialog.findViewById(R.id.text_8)
        this.text9 = dialog.findViewById(R.id.text_9)
        this.text0 = dialog.findViewById(R.id.text_0)
        this.textErase = dialog.findViewById(R.id.text_erase)
        this.textReset = dialog.findViewById(R.id.text_reset)
        this.textPassword = dialog.findViewById(R.id.text_password)
        this.textWarning = dialog.findViewById(R.id.text_warning)
        this.btnConfirm = dialog.findViewById(R.id.btn_confirm)
        this.btnCancel = dialog.findViewById(R.id.btn_cancel)
    }
}

