package com.artkachenko.core_api.network.api

import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity

interface RecipeApi {

    suspend fun getRecipeList(offset: Int, vararg filters: Pair<String, List<String>>): List<RecipeEntity>

    suspend fun getRecipeDetail(id: Long) : RecipeDetailModel

    suspend fun parseIngredients(ingredients: List<String>): List<Ingredient>
}