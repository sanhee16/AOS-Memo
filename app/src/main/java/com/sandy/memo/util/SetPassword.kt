package com.sandy.memo.util

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.constraintlayout.widget.ConstraintLayout
import com.sandy.memo.R
import com.sandy.memo.databinding.SetPasswordBinding

// https://developer.android.com/codelabs/advanced-android-kotlin-training-custom-views#3
class SetPassword @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val dialog = Dialog(context)
    

    fun start() {
        // https://stackoverflow.com/questions/38476045/android-custom-dialog-with-button-onclick-event/38476200
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.set_password)
        dialog.show()
    }
}