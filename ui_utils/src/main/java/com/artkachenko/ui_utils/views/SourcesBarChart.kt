package com.artkachenko.ui_utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.AnimationUtils
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.dp
import com.artkachenko.ui_utils.dpF
import com.artkachenko.ui_utils.themes.Themes

class SourcesBarChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private val rect = RectF()

    private val sources = mutableListOf<Double>()
    private val sourcesIncrement = mutableListOf<Float>()
    private val sourcesIncrementOriginal = mutableListOf<Float>()
    private var isProgress = false

    private var distance = 26F

    private var customWidth = 26F

    private var calculatedColumnSize = 0

    private var calculatedOffSetSize = 0

    private var maxSource = 0.0

    private val leftOffset = dp(24)

    private val rightOffSet = dp(24)

    private val axisPaint = Paint().apply {
        strokeWidth = dpF(1F)
        isAntiAlias = true
        color = ContextCompat.getColor(context, R.color.text_secondary)
    }

    private val paintsList = List(15) { index: Int ->
        Paint().apply {
            strokeWidth = customWidth
            isAntiAlias = true
            color = ContextCompat.getColor(context, Themes.chartColors[index])
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val measuredWidth = measureDimension(desiredWidth, widthMeasureSpec)
        val measuredHeight = measureDimension(desiredHeight, heightMeasureSpec)

        rect.set(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())

        setMeasuredDimension(
            measuredWidth,
            measuredHeight
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val offset = (rect.width() - leftOffset - rightOffSet) / (sources.size - 1)

        val bottom = rect.bottom - dp(1)

        val maxValue = sources.firstOrNull() ?: 0.0
        val maxIncrement = sourcesIncrement.firstOrNull() ?: 0F

        isProgress = maxValue >= maxIncrement

        if (isProgress) {
            sources.forEachIndexed { index, d ->
                val increment = sourcesIncrement[index]
                val initialX = leftOffset + customWidth / 2 + (offset * index)
                val finalX = leftOffset + customWidth / 2 + (offset * index)
                val finalY = (bottom - (bottom * increment / maxSource)).toFloat()
                canvas.drawLine(initialX, bottom, finalX, finalY, paintsList[index])
                sourcesIncrement[index] = increment + sourcesIncrementOriginal[index]
            }
            invalidate()
        } else {
            sources.forEachIndexed { index, d ->
                val initialX = leftOffset + customWidth / 2 + (offset * index)
                val finalX = leftOffset + customWidth / 2 + (offset * index)
                val finalY = (bottom - (bottom * d / maxSource)).toFloat()
                canvas.drawLine(initialX, bottom, finalX, finalY, paintsList[index])
            }
        }
        val lineLeftOffset = leftOffset - dpF(6F)

        canvas.drawLine(lineLeftOffset, 0F, lineLeftOffset, bottom, axisPaint)
        canvas.drawLine(lineLeftOffset, bottom, rect.width() - rightOffSet + dpF(8F), bottom, axisPaint)
    }

    fun setData(data: List<Double>?) {
        if (data.isNullOrEmpty()) return
        sources.clear()
        sourcesIncrement.clear()
        val sortedData = data.sortedDescending()
        maxSource = sortedData.firstOrNull() ?: 0.0
        sources.addAll(sortedData.take(15))
        sources.forEach {
            val increment = it.toFloat() / AnimationUtils.chartAnimationSteps
            sourcesIncrement.add(increment)
            sourcesIncrementOriginal.add(increment)
        }
        invalidate()
    }
}