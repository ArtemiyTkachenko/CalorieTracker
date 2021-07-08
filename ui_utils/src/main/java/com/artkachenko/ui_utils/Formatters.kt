package com.artkachenko.ui_utils

import java.time.format.DateTimeFormatter

object Formatters {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd")
    val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE")
    val monthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM")
}