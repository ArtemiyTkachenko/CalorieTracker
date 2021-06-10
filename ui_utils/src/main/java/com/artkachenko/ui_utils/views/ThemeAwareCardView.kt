package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.themes.BaseCoroutineView
import com.artkachenko.ui_utils.themes.BaseCoroutineViewImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareCardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attributeSet, defStyle),
    BaseCoroutineView by BaseCoroutineViewImpl() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        scope.launch {
            ThemeManager.themeFlow.collect {
                val textViewTheme = it.textViewTheme
                setBackgroundColor(ContextCompat.getColor(context, textViewTheme.backgroundColor))
            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }
}