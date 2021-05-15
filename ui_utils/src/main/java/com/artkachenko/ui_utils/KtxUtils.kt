package com.artkachenko.ui_utils

import android.content.Context
import android.view.View

fun Context.dp(value: Float): Int {
    return (this.resources.displayMetrics.density * value).toInt()
}

fun Context.dpF(value: Float): Float {
    return (this.resources.displayMetrics.density * value)
}

fun View.dp(value: Float): Int {
    return this.context.dp(value)
}

fun View.dp(value: Int): Int {
    return this.context.dp(value.toFloat())
}

fun View.dpF(value: Float): Float {
    return this.context.dpF(value)
}