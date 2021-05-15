package com.artkachenko.ui_utils

import java.time.format.DateTimeFormatter

object Formatters {
    val dateFormatter = DateTimeFormatter.ofPattern("dd")
    val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    val monthFormatter = DateTimeFormatter.ofPattern("MMM")
}