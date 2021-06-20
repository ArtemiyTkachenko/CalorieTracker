package com.artkachenko.ui_utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.dpF
import kotlin.math.absoluteValue

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

    private var animationDuration = 300

    private var neutralRatePosition = 0F
    private var lowRatePosition = 0F

    private var fatRateSpan: Float = 0F
    private var proteinRateSpan: Float = 0F
    private var carbRateSpan: Float = 0F

    private var progressLengthFat = 0F

    private var progressLengthProtein = 0F

    private var progressLengthCarbs = 0F

    private var fatIncrement = 0F

    private var proteinIncrement = 0F

    private var carbsIncrement = 0F

    private var isProgress = false

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

        isProgress = fatRateSpan.absoluteValue >= progressLengthFat && proteinRateSpan.absoluteValue >= progressLengthProtein && carbRateSpan.absoluteValue >= progressLengthCarbs

        if (isProgress) {
            canvas.save()
            canvas.drawArc(
                rect,
                startPosition, -progressLengthFat, false, fatPaint
            )
            canvas.drawArc(
                rect,
                neutralRatePosition, -progressLengthProtein, false, proteinPaint
            )
            canvas.drawArc(
                rect,
                lowRatePosition, -progressLengthCarbs, false, carbsPaint
            )
            progressLengthFat += fatIncrement
            progressLengthProtein += proteinIncrement
            progressLengthCarbs += carbsIncrement
            invalidate()
            canvas.restore()
        } else {
            canvas.save()
            canvas.drawArc(
                rect,
                startPosition, fatRateSpan, false, fatPaint
            )
            canvas.drawArc(
                rect,
                neutralRatePosition, proteinRateSpan, false, proteinPaint
            )
            canvas.drawArc(
                rect,
                lowRatePosition, carbRateSpan, false, carbsPaint
            )
            canvas.restore()
        }
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
        val fatSpan = 360 * -fatRate / 100
        val proteinSpan = 360 * -proteinRate / 100
        val carbSpan = 360 * -carbRate / 100

        val neutralRatePosition = 270 + fatSpan
        val lowRatePosition = neutralRatePosition + proteinSpan

        this.fatRateSpan = fatSpan.toFloat()
        this.proteinRateSpan = proteinSpan.toFloat()
        this.carbRateSpan = carbSpan.toFloat()

        this.neutralRatePosition = neutralRatePosition.toFloat()
        this.lowRatePosition = lowRatePosition.toFloat()

        fatIncrement = (360 / fatRateSpan).absoluteValue * 3
        proteinIncrement = (360 / proteinRateSpan).absoluteValue * 3
        carbsIncrement = (360 / carbRateSpan).absoluteValue * 3
    }

    private fun applyPaintStyle(paint: Paint) {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dpF(7F)
        paint.isAntiAlias = true
    }

}