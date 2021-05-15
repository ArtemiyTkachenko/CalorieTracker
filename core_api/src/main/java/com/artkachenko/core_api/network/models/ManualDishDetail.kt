package com.artkachenko.core_api.network.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "manual_dishes")
@Serializable
@Parcelize
data class ManualDishDetail(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    val extendedIngredients: List<Ingredient>? = null,
    val date: Long? = null
) : Parcelable, HasId