package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.themes.ViewScopeProvider
import com.artkachenko.ui_utils.themes.ViewScopeProviderImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attributeSet, defStyle),
    ViewScopeProvider by ViewScopeProviderImpl() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        scope.launch {
            ThemeManager.themeFlow.collect {
                val textViewTheme = it.textViewTheme
                setBackgroundColor(ContextCompat.getColor(context, textViewTheme.backgroundColor))
                setTextColor(ContextCompat.getColor(context, textViewTheme.textColor))
            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }
}