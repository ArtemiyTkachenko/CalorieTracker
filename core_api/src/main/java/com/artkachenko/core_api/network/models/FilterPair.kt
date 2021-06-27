package com.artkachenko.core_api.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FilterPair(private val keyValue: Pair<String, String>) : Map.Entry<String, String>, Parcelable {
    override val key: String
        get() = keyValue.first
    override val value: String
        get() = keyValue.second
}