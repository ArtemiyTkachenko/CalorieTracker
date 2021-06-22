package com.artkachenko.core_api.network.models

import android.os.Parcelable
import com.artkachenko.core_api.interfaces.HasId
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class RecipeEntity(
    @SerialName("id")
    override val id: Long = 0,
    @SerialName("title")
    val title: String? = null,
    @SerialName("readyInMinutes")
    val readyInMinutes: Int? = null,
    @SerialName("totalStars")
    val image: String? = null,
    @SerialName("spoonacularScore")
    val spoonacularScore: Double? = null,
    @SerialName("healthScore")
    val healthScore: Double? = null
): Parcelable, HasId

@Serializable
data class RecipeResultsWrapper(val results: List<RecipeEntity>)