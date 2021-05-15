package com.artkachenko.core_impl.repositories

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_impl.network.RecipeApiImpl
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi
) : RecipeRepository {

    override suspend fun getRecipeList(page: Int): List<RecipeEntity> {
        return recipeApi.getRecipeList(page)
    }

    override suspend fun getRecipeDetail(id: Long): RecipeDetailModel {
        return recipeApi.getRecipeDetail(id)
    }
}