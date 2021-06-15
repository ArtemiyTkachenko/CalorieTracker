package com.artkachenko.ui_utils.themes

import androidx.annotation.ColorRes
import com.artkachenko.ui_utils.R

data class ChipTheme (
   @ColorRes
   val checkedStateList: Int = R.color.background_color_checked_state_list,
   @ColorRes
   val checkedTextStateList: Int = R.color.text_color_chip_state_list
)