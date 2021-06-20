package com.artkachenko.ui_utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.dpF

class NutritionPieChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    View(context, attributeSet, defStyle) {

    var startPosition = 270F

    var rect = RectF(
        dpF(5F),
        dpF(5F),
        dpF(133F),
        dpF(133F)
    )

    var neutralRatePosition = 0F
    var lowRatePosition = 0F

    var highRateSpan: Float = 0F
    var neutralRateSpan: Float = 0F
    var lowRateSpan: Float = 0F

    var fatPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.green_200)
        applyPaintStyle(this)
    }
    var proteinPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.yellow_200)
        applyPaintStyle(this)
    }
    var carbsPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.red_200)
        applyPaintStyle(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(
            rect,
            startPosition, highRateSpan, false, fatPaint
        )
        canvas.drawArc(
            rect,
            neutralRatePosition, neutralRateSpan, false, proteinPaint
        )
        canvas.drawArc(
            rect,
            lowRatePosition, lowRateSpan, false, carbsPaint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }

        return result
    }


    fun setChartData(fatRate: Long, proteinRate: Long, carbRate: Long) {
        val highRateSpan = 360 * -fatRate / 100
        val neutralRateSpan = 360 * -proteinRate / 100
        val lowRateSpan = 360 * -carbRate / 100

        val neutralRatePosition = 270 + highRateSpan
        val lowRatePosition = neutralRatePosition + neutralRateSpan

        this.highRateSpan = highRateSpan.toFloat()
        this.neutralRateSpan = neutralRateSpan.toFloat()
        this.lowRateSpan = lowRateSpan.toFloat()

        this.neutralRatePosition = neutralRatePosition.toFloat()
        this.lowRatePosition = lowRatePosition.toFloat()

    }

    private fun applyPaintStyle(paint: Paint) {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dpF(7F)
        paint.isAntiAlias = true
    }

}