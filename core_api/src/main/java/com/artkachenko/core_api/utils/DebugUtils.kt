package com.artkachenko.core_api.utils

import android.util.Log
import androidx.multidex.BuildConfig

fun Any.debugLog(message: String, tag: String? = null) {
//    if (BuildConfig.DEBUG) {
    val actualTag = tag ?: this::class.java.simpleName
    Log.d(actualTag, message)
//    }
}

fun Any.debugVerbose(message: String) {
    if (BuildConfig.DEBUG) {
        Log.v(this::class.java.simpleName, message)
    }
}

fun Any.errorLog(message: String) {
    if (BuildConfig.DEBUG) {
        Log.e(this::class.java.simpleName, message)
    }
}