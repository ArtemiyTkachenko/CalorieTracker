package com.artkachenko.core_impl.repositories

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.ConvertedAmount
import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_impl.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RecipeRepository {

    override suspend fun getRecipeList(offset: Int, vararg filters: Pair<String, List<String>>): List<RecipeEntity> {
        return withContext(dispatcher) {
            recipeApi.getRecipeList(offset, *filters)
        }
    }

    override suspend fun getRecipeDetail(id: Long): RecipeDetailModel {
        return withContext(dispatcher) {
            recipeApi.getRecipeDetail(id)
        }
    }

    override suspend fun parseIngredients(ingredients: List<String>): List<Ingredient> {
        return withContext(dispatcher) {
            recipeApi.parseIngredients(ingredients)
        }
    }

    override suspend fun convertIngredients(vararg filters: Pair<String, List<String>>): ConvertedAmount {
        return withContext(dispatcher) {
            recipeApi.convertIngredients(*filters)
        }
    }

    override suspend fun getRecipesById(ids: List<Long>): List<RecipeEntity> {
        return withContext(dispatcher) {
            recipeApi.getRecipesById(ids)
        }
    }
}