package com.artkachenko.core_impl.utils

import android.util.Log
import androidx.multidex.BuildConfig

fun Any.debugLog(message: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.simpleName, message)
    }
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