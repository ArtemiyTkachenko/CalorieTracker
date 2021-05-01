package com.artkachenko.core_api.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeEntity(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("currentRank")
    val currentRank: Int? = null,
    @SerialName("totalStars")
    val totalStars: Int? = null,
    @SerialName("totalWordsMastered")
    val totalWordsMastered: Int? = null,
)