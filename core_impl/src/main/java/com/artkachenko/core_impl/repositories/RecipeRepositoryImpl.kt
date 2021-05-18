package com.artkachenko.core_impl.repositories

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_impl.IoDispatcher
import com.artkachenko.core_impl.network.RecipeApiImpl
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RecipeRepository {

    override suspend fun getRecipeList(page: Int): List<RecipeEntity> {
        val list: List<RecipeEntity>
        withContext(dispatcher) {
            list = recipeApi.getRecipeList(page)
        }
        return list
    }

    override suspend fun getRecipeDetail(id: Long): RecipeDetailModel {
        val model: RecipeDetailModel
        withContext(dispatcher) {
            model = recipeApi.getRecipeDetail(id)
        }
        return model
    }

    override suspend fun parseIngredients(ingredients: List<String>): List<Ingredient> {
        val list: List<Ingredient>
        withContext(dispatcher) {
            list = recipeApi.parseIngredients(ingredients)
        }
        return list
    }
}