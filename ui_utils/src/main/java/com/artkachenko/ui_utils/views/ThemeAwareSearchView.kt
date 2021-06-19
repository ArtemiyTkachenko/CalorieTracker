package com.artkachenko.ui_utils.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.artkachenko.ui_utils.themes.ViewScopeProvider
import com.artkachenko.ui_utils.themes.ViewScopeProviderImpl
import com.artkachenko.ui_utils.themes.ThemeManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeAwareSearchView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : SearchView(context, attributeSet, defStyle),
    ViewScopeProvider by ViewScopeProviderImpl() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        scope.launch {
            ThemeManager.themeFlow.collect {
                val searchViewTheme = it.searchViewTheme
                val editText = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                editText.setHintTextColor(ContextCompat.getColor(context, searchViewTheme.hintColor))
                editText.setTextColor(ContextCompat.getColor(context, searchViewTheme.textColor))
                val icon = findViewById<ImageView>(androidx.appcompat.R.id.search_button)
                icon.setColorFilter(ContextCompat.getColor(context, searchViewTheme.iconTint), android.graphics.PorterDuff.Mode.SRC_IN)

            }
        }
    }

    override fun onDetachedFromWindow() {
        parentJob.cancel()
        super.onDetachedFromWindow()
    }
}