package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.R
import com.artkachenko.ui_utils.themes.BaseCoroutineView
import com.artkachenko.ui_utils.themes.BaseCoroutineViewImpl
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
    BaseCoroutineView by BaseCoroutineViewImpl() {

    private var firstDraw = true

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
}