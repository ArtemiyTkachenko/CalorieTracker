package com.artkachenko.core_api.network.repositories

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.RecipeEntity

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi
) : RecipeRepository {

    override suspend fun getRecipeList(page: Int): RecipeEntity {
        return recipeApi.getRecipeList(page)
    }
}