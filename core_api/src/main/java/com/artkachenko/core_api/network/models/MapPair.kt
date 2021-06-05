package com.artkachenko.core_api.network.models

class MapPair(private val keyValue: Pair<String, String>) : Map.Entry<String, String> {
    override val key: String
        get() = keyValue.first
    override val value: String
        get() = keyValue.second
}