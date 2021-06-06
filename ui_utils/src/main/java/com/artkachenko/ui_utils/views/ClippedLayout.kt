package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout

class ClippedLinearLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle) {

    init {
        clipToOutline = true
    }
}

class ClippedConstraintLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : ThemeAwareConstraintLayout(context, attributeSet, defStyle) {

    init {
        clipToOutline = true
    }
}