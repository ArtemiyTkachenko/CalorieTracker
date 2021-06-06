package com.artkachenko.ui_utils.themes

import android.content.res.ColorStateList
import com.artkachenko.ui_utils.R

object Themes {
    val backgroundColorDark = android.R.color.background_dark
    val backgroundColorLight = android.R.color.background_light

    val textColorDark = R.color.white
    val textColorLight = R.color.black

    private val states = arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_pressed)
    )

    private val backgroundColorsDark = intArrayOf(
        android.R.color.darker_gray,
        R.color.text_secondary,
        R.color.purple_200,
        R.color.purple_200,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_200
    )

    private val backgroundColorsLight = intArrayOf(
        android.R.color.darker_gray,
        R.color.text_secondary,
        R.color.purple_500,
        R.color.purple_500,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_500
    )

    private val textColorsDark = intArrayOf(
        R.color.white,
        R.color.text_secondary,
        R.color.purple_200,
        R.color.purple_200,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_200
    )

    private val textColorsLight = intArrayOf(
        R.color.black,
        R.color.text_secondary,
        R.color.purple_500,
        R.color.purple_500,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_500
    )

    val backgroundColorStateListDark = ColorStateList(states, backgroundColorsDark)

    val backgroundColorStateListLight = ColorStateList(states, backgroundColorsLight)

    val textColorStateListDark = ColorStateList(states, textColorsDark)

    val textColorStateListLight = ColorStateList(states, textColorsLight)

}