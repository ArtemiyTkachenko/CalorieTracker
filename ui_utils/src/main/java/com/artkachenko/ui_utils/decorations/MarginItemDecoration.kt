package com.artkachenko.ui_utils.decorations

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.ui_utils.R

class MarginItemDecoration(context : Context, private val resId : Int = 0, private val marginLeft : Int = 0, private val marginRight : Int = 0, private val excludeLast: Boolean = false) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    private val dividerPaint = Paint().also { paint ->
        paint.color = ContextCompat.getColor(context, R.color.purple_500)
        paint.style = Paint.Style.FILL
    }

    init {
        divider = ContextCompat.getDrawable(context, R.drawable.recycler_divider)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        val endAt = if (excludeLast) childCount.minus(1) else childCount

        val density = parent.resources.displayMetrics.density

        val left = 0 + marginLeft * density
        val right = parent.width - marginRight * density
        for (i in 0 until endAt) {
            val child = parent.getChildAt(i)
            child?.let {
                val top: Float = child.bottom - 1 * density
                val bottom = top + 1 * density
                c.drawRect(left, top, right, bottom, dividerPaint)
            }
        }
    }
}