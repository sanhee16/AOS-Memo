package com.sandy.memo.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.sandy.memo.R

class DialogType1(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var btnConfirm: TextView
    private lateinit var btnCancel: TextView
    private lateinit var message: TextView

    fun start(message: String) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_type1)
        dialog.setCancelable(true)

        this.message = dialog.findViewById(R.id.text_message)
        this.btnConfirm = dialog.findViewById(R.id.btn_confirm)
        this.btnCancel = dialog.findViewById(R.id.btn_cancel)
        this.message.text = message

        btnConfirm.setOnClickListener {
            // TODO: 2021-12-20  change : https://dkfk2747.tistory.com/22 activity에서 처리할 수 있게 변경해야 함 
//            dialog.dismiss()
            listener.onConfirmClick()
        }
        btnCancel.setOnClickListener {
            listener.onClickCancel()
        }
        dialog.show()
    }
    fun dismiss() = dialog.dismiss()

    interface OnClickListener {
        fun onConfirmClick()
        fun onClickCancel()
    }
    private lateinit var listener: OnClickListener
    fun setClickListener(listener: OnClickListener) {
        this.listener = listener
    }
}