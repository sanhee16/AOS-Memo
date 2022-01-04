package com.sandy.memo.util

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sandy.memo.R

// https://developer.android.com/codelabs/advanced-android-kotlin-training-custom-views#3
class SetPassword @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.set_password, this)
    }
}