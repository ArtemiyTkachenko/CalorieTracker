package com.artkachenko.ui_utils.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.AnimationUtils
import com.artkachenko.ui_utils.R

class ProgressChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    AppCompatImageView(context, attributeSet, defStyle) {

    private var density = context.resources.displayMetrics.density

    private var basePosition = 0F
    private var startDrawPosition = 180F

    private var rectWidth = 230F * density
    private var rectHeight = 230F * density
    private var start = 25F * density
    private var top = 25F * density

    private var rectF = RectF(start, top, rectWidth, rectHeight)

    private var fillLength = 0F
    private var progressLength = 0F
    private var progressIncrement = 0F

    private var isProgress = false

    private var textSizeF = 12F * density

    private var fillColor = Paint().apply {
        this.color = ContextCompat.getColor(context, R.color.red_200)
        setArcPaintParams()
    }
    private var backGroundColor = Paint().apply {
        this.color = ContextCompat.getColor(context, R.color.green_200)
        setArcPaintParams()
    }

    private var backGroundColorEmpty = Paint().apply {
        this.color = ContextCompat.getColor(context, R.color.green_200)
        setArcPaintParams()
    }

    private var dividerColor = Paint().apply {
        this.color = Color.BLACK
        setArcPaintParams()
        alpha = 70
    }

    private var numberTextPaint = Paint().apply {
        this.color = ContextCompat.getColor(context, R.color.text_secondary)
        this.textSize = textSizeF
        this.isAntiAlias = true
        this.strokeCap = Paint.Cap.BUTT
        this.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(rectF, basePosition, -180F, false, backGroundColor)

        canvas.drawText("0%", start / 4 - 2 * density, rectHeight / 2 + top / 2, numberTextPaint)
        canvas.drawText("25%", rectWidth / 5 - start / 2, rectHeight / 5, numberTextPaint)
        canvas.drawText(
            "50%",
            rectWidth / 2 + textSizeF / 2 - 2 * density,
            top - textSizeF + 3 * density,
            numberTextPaint
        )
        canvas.drawText("75%", rectWidth / 5 * 4 + start / 3 * 2, rectHeight / 5, numberTextPaint)
        canvas.drawText("100%", rectWidth + start / 4, rectHeight / 2 + top / 2, numberTextPaint)

        isProgress = fillLength >= progressLength

        if (isProgress) {
            canvas.save()
            canvas.drawArc(rectF, startDrawPosition, progressLength, false, fillColor)

            progressLength += progressIncrement
            invalidate()
            canvas.restore()
        } else {
            canvas.save()
            canvas.drawArc(rectF, startDrawPosition, fillLength, false, fillColor)

            canvas.restore()
        }

        for (i in 1.until(4)) {
            if (startDrawPosition + (44.8F * i) < fillLength) {
                canvas.drawArc(rectF, startDrawPosition + (44.8F * i), 0.4F, false, dividerColor)
            } else {
                canvas.drawArc(
                    rectF,
                    startDrawPosition + (44.8F * i),
                    0.4F,
                    false,
                    backGroundColorEmpty
                )
            }
        }
    }

    fun setData(count: Long, total: Long) {
        val countFloat = count.toFloat()
        val totalFloat = total.toFloat()
        this.progressLength = 0F
        if (count > total) {
            this.fillLength = 180F
        } else {
            this.fillLength = countFloat / totalFloat * 180
        }
        progressIncrement = fillLength / AnimationUtils.chartAnimationSteps
        invalidate()
    }

    private fun Paint.setArcPaintParams() {
        this.strokeWidth = 20F
        this.isAntiAlias = true
        this.strokeCap = Paint.Cap.BUTT
        this.style = Paint.Style.STROKE
    }
}