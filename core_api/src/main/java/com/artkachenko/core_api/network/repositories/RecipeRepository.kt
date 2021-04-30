package com.artkachenko.core_api.network.repositories

import com.artkachenko.core_api.network.models.RecipeEntity

interface RecipeRepository {

    suspend fun getRecipeList(page: Int): List<RecipeEntity>

    suspend fun getRecipeDetail(id: Int) : RecipeEntity
}