package com.artkachenko.core_api.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FilterPair(private val keyValue: Pair<String, FilterItemWrapper>) : Map.Entry<String, FilterItemWrapper>, Parcelable {
    override val key: String
        get() = keyValue.first
    override val value: FilterItemWrapper
        get() = keyValue.second
}