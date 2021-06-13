package com.artkachenko.core_impl.utils

import android.content.SharedPreferences
import com.artkachenko.core_api.utils.PrefManager
import kotlin.reflect.KProperty


class PrefManagerImpl(private val prefs: SharedPreferences) : PrefManager {

    private val isDarkThemeKey = "IS_DARK_THEME"

    private val desiredCaloriesAmount = "DESIRED_AMOUNT"

    override var isDarkTheme by BooleanPreference(isDarkThemeKey, false)

    override var desiredCalories by IntPreference(desiredCaloriesAmount, 2000)

    class BooleanPreference(val name: String, val default: Boolean = false) {

        operator fun getValue(thisRef: PrefManagerImpl, property: KProperty<*>): Boolean =
            thisRef.prefs.getBoolean(name, default)

        operator fun setValue(thisRef: PrefManagerImpl, property: KProperty<*>, value: Boolean) =
            thisRef.prefs.edit().putBoolean(name, value).apply()
    }

    class StringPreference(val name: String, val default: String ?= null) {

        operator fun getValue(thisRef: PrefManagerImpl, property: KProperty<*>): String? =
            thisRef.prefs.getString(name, default)

        operator fun setValue(thisRef: PrefManagerImpl, property: KProperty<*>, value: String?) =
            thisRef.prefs.edit().putString(name, value).apply()
    }

    class IntPreference(val name: String, val default: Int = 0) {

        operator fun getValue(thisRef: PrefManagerImpl, property: KProperty<*>): Int =
            thisRef.prefs.getInt(name, default)

        operator fun setValue(thisRef: PrefManagerImpl, property: KProperty<*>, value: Int) =
            thisRef.prefs.edit().putInt(name, value).apply()
    }

    class LongPreference(val name: String, val default: Long = 0L) {

        operator fun getValue(thisRef: PrefManagerImpl, property: KProperty<*>): Long =
            thisRef.prefs.getLong(name, default)

        operator fun setValue(thisRef: PrefManagerImpl, property: KProperty<*>, value: Long) =
            thisRef.prefs.edit().putLong(name, value).apply()
    }
}