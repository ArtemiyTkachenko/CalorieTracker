package com.artkachenko.ui_utils.themes

import android.content.res.ColorStateList
import com.artkachenko.ui_utils.R

object Themes {
    val backgroundColorDark = android.R.color.background_dark
    val backgroundColorLight = android.R.color.background_light

    val textColorDark = R.color.white
    val textColorLight = R.color.black
    val textColorSecondary = R.color.text_secondary

    private val states = arrayOf(
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_pressed),
        intArrayOf(android.R.attr.state_enabled),
    )

    private val backgroundColorsDark = intArrayOf(
        R.color.text_secondary,
        R.color.purple_200,
        R.color.purple_200,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_200,
        android.R.color.darker_gray
    )

    private val backgroundColorsLight = intArrayOf(
        android.R.color.darker_gray,
        R.color.text_secondary,
        R.color.purple_500,
        R.color.purple_500,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_500,
        android.R.color.darker_gray
    )

    private val textColorsDark = intArrayOf(
        R.color.text_secondary,
        R.color.purple_200,
        R.color.purple_200,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_200,
        R.color.white
    )

    private val textColorsLight = intArrayOf(
        R.color.text_secondary,
        R.color.purple_500,
        R.color.purple_500,
        R.color.text_secondary,
        R.color.text_secondary,
        R.color.purple_500,
        R.color.black,
    )

    val backgroundColorStateListDark = ColorStateList(states, backgroundColorsDark)

    val backgroundColorStateListLight = ColorStateList(states, backgroundColorsLight)

    val textColorStateListDark = ColorStateList(states, textColorsDark)

    val textColorStateListLight = ColorStateList(states, textColorsLight)

}