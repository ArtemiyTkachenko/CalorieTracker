package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.artkachenko.ui_utils.themes.BaseCoroutineView
import com.artkachenko.ui_utils.themes.BaseCoroutineViewImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareChip @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : Chip(context, attributeSet, defStyle),
    BaseCoroutineView by BaseCoroutineViewImpl() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        scope.launch {
            ThemeManager.themeFlow.collect {
                val chipViewTheme = it.chipTheme
                chipBackgroundColor = chipViewTheme.chipBackgroundColor
                setTextColor(chipViewTheme.textColor)
            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }
}