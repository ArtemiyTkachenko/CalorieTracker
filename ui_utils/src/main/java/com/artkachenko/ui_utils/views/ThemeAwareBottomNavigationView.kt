package com.artkachenko.ui_utils.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.dpF
import com.artkachenko.ui_utils.themes.ViewScopeProvider
import com.artkachenko.ui_utils.themes.ViewScopeProviderImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : BottomNavigationView(context, attributeSet, defStyle),
    ViewScopeProvider by ViewScopeProviderImpl() {

    private var firstDraw = true

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.text_secondary)
        strokeWidth = dpF(1F)
        alpha = 70
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scope.launch {
            ThemeManager.themeFlow.collect {
                if (!firstDraw) delay(225)
                firstDraw = false
                val theme = it.bottomNavigationViewTheme
                setBackgroundColor(ContextCompat.getColor(context, theme.backgroundColor))
                itemIconTintList = ContextCompat.getColorStateList(context, theme.checkedStateList)
                itemTextColor = ContextCompat.getColorStateList(context, theme.checkedTextStateList)
            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(0F, dpF(1F) , width.toFloat(), dpF(1F), paint)
    }
}