package com.artkachenko.ui_utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet, defStyle: Int = 0) : AppCompatImageView(context, attributeSet, defStyle) {

    private val path = Path()

    override fun onDraw(canvas: Canvas?) {
        path.addCircle(width/2F, height/2F, width/2F, Path.Direction.CW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }
}