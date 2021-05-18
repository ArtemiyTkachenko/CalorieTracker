package com.artkachenko.core_api.network.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "recipe_details")
data class RecipeDetailModel(
    @PrimaryKey
    override val id: Long = 0L,
    val title: String? = "",
    val readyInMinutes: Int? = 0,
    val image: String? = null,
    val imageType: String? = null,
    val instructions: String? = null,
    val summary: String? = null,
    val vegetarian: Boolean? = false,
    val vegan: Boolean? = false,
    val glutenFree: Boolean? = false,
    val dairyFree: Boolean? = false,
    val veryHealthy: Boolean? = false,
    val cheap: Boolean? = false,
    val veryPopular: Boolean? = false,
    val sustainable: Boolean? = false,
    val weightWatcherSmartPoints: Int? = 0,
    val gaps: String? = null,
    val lowFodmap: Boolean? = false,
    val ketogenic: Boolean? = false,
    val whole30: Boolean? = false,
    val servings: Int? = 0,
    val sourceUrl: String? = null,
    val spoonacularSourceUrl: String? = null,
    val aggregateLikes: Int? = 0,
    val creditText: String? = null,
    val sourceName: String? = null,
    val nutrition: Nutrition? = null,
    val extendedIngredients: List<Ingredient>? = null,
    val diets: List<String>
) : Parcelable, HasId

@Entity(tableName = "ingredients")
@Serializable
@Parcelize
data class Ingredient(
    @PrimaryKey
    override val id: Long,
    val aisle: String? = null,
    val image: String? = null,
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null,
    val unitShort: String? = null,
    val unitLong: String? = null,
    val originalString: String? = null,
    val nutrition: Nutrition? = null
) : Parcelable, HasId

@Serializable
@Parcelize
data class Nutrition(
    val nutrients: List<NutritionItem>? = null,
    val properties: List<PropertiesItem>? = null,
    val caloricBreakdown: CaloricBreakdown? = null,
    val weightPerServing: WeightPerServing? = null
) : Parcelable

@Serializable
@Parcelize
data class NutritionItem(
    val title: String? = null,
    val amount: Double? = null,
    val unit: String? = null,
    val percentOfDailyNeeds: Double? = null
) : Parcelable

@Serializable
@Parcelize
data class PropertiesItem(
    val title: String?,
    val amount: Double?,
    val unit: String
) : Parcelable

@Serializable
@Parcelize
data class CaloricBreakdown(
    val percentProtein: Double,
    val percentFat: Double,
    val percentCarbs: Double
) : Parcelable

@Serializable
@Parcelize
data class WeightPerServing(
    val amount: Double,
    val unit: String
) : Parcelable

@Serializable
@Parcelize
data class IngredientsWrapper(
    val ingredientList: List<String>
) : Parcelable {
    override fun toString(): String {
        return super.toString()
    }
}