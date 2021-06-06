package com.artkachenko.ui_utils.themes

import android.content.res.ColorStateList
import com.artkachenko.ui_utils.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.coroutines.CoroutineContext

object ThemeManager {

    private val backgroundColorDark = android.R.color.background_dark
    private val backgroundColorLight = android.R.color.background_light

    private val textColorDark = R.color.white
    private val textColorLight = R.color.black

    private val states = arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_pressed)
    )

    private val backgroundColorsDark = intArrayOf(
        android.R.color.darker_gray,
        R.color.text_secondary,
        R.color.purple_200,
        R.color.text_secondary,
        R.color.purple_200
    )

    private val backgroundColorsLight = intArrayOf(
        android.R.color.darker_gray,
        R.color.text_secondary,
        R.color.purple_500,
        R.color.text_secondary,
        R.color.purple_500
    )

    private val textColorsDark = intArrayOf(
        R.color.white,
        R.color.text_secondary,
        R.color.purple_200,
        R.color.text_secondary,
        R.color.purple_200
    )

    private val textColorsLight = intArrayOf(
        R.color.black,
        R.color.text_secondary,
        R.color.purple_500,
        R.color.text_secondary,
        R.color.purple_500
    )

    val backgroundColorStateListDark = ColorStateList(states, backgroundColorsDark)

    val backgroundColorStateListLight = ColorStateList(states, backgroundColorsLight)

    val textColorStateListDark = ColorStateList(states, textColorsDark)

    val textColorStateListLight = ColorStateList(states, textColorsLight)

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)

    val themeFlow: SharedFlow<Theme>
        get() = _themeFlow

    private val _themeFlow = MutableSharedFlow<Theme>()
    var theme = Theme.LIGHT
        set(value) {
            field = value
            scope.launch {
                _themeFlow.emit(value)
            }
        }

    interface ThemeChangedListener {

        fun onThemeChanged(theme: Theme)
    }

    enum class Theme(
        val viewGroupTheme: ViewGroupTheme,
        val bottomNavigationViewTheme: BottomNavigationViewTheme,
        val textViewTheme: TextViewTheme,
        val imageViewTheme: ImageViewTheme,
        val chipTheme: ChipTheme
    ) {
        DARK(
            viewGroupTheme = ViewGroupTheme(
                backgroundColor = backgroundColorDark
            ),
            bottomNavigationViewTheme = BottomNavigationViewTheme(
                backgroundColor = backgroundColorDark
            ),
            textViewTheme = TextViewTheme(
                textColor = textColorDark,
                backgroundColor = backgroundColorDark
            ),
            imageViewTheme = ImageViewTheme(
                backgroundColor = backgroundColorDark
            ),
            chipTheme = ChipTheme(
                chipBackgroundColor = backgroundColorStateListDark,
                textColor = textColorStateListDark
            )
        ),
        LIGHT(
            viewGroupTheme = ViewGroupTheme(
                backgroundColor = backgroundColorLight
            ),
            bottomNavigationViewTheme = BottomNavigationViewTheme(
                backgroundColor = backgroundColorLight
            ),
            textViewTheme = TextViewTheme(
                textColor = textColorLight,
                backgroundColor = backgroundColorLight
            ),
            imageViewTheme = ImageViewTheme(
                backgroundColor = backgroundColorLight
            ),
            chipTheme = ChipTheme(
                chipBackgroundColor = backgroundColorStateListLight,
                textColor = textColorStateListLight
            )
        );
    }
}