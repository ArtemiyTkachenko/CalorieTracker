package com.artkachenko.core_api.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterWrapper(val filters: MutableMap<String, MutableList<FilterItemWrapper>>): Parcelable {

    fun extractFirstKey(): String {
        return filters.keys.first()
    }
}

@Parcelize
class FilterItemWrapper(val value: String, var isChecked: Boolean = false) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FilterItemWrapper

        if (!value.equals(other.value, true)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result *= 31
        return result
    }
}