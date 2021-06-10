package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.ui_utils.themes.BaseCoroutineView
import com.artkachenko.ui_utils.themes.BaseCoroutineViewImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ScrollView(context, attributeSet, defStyle),
    BaseCoroutineView by BaseCoroutineViewImpl() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scope.launch {
            ThemeManager.themeFlow.collect {
                val theme = it.cardViewTheme
                setBackgroundColor(ContextCompat.getColor(context, theme.cardBackground))
            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }
}