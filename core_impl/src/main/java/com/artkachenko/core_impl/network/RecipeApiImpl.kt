package com.artkachenko.core_impl.network

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class RecipeApiImpl @Inject constructor(private val client: HttpClient) : RecipeApi {

    override suspend fun getRecipeList(
        offset: Int,
        vararg filters: Pair<String, List<String>>
    ): List<RecipeEntity> {
        return client.get<RecipeResultsWrapper>(NetworkEndpoints.RecipeSearch) {
            parametersOf(pairs = filters).forEach { s, list ->
                this.url.parameters.appendAll(s, list)
            }
        }.results
    }

    override suspend fun getRecipeDetail(id: Long): RecipeDetailModel {
        return client.get(NetworkEndpoints.getRecipeDetailEndPoint(id)) {
            parameter("includeNutrition", true)
        }
    }

    override suspend fun parseIngredients(ingredients: List<String>): List<Ingredient> {
        return emptyList()
    }

    override suspend fun convertIngredients(vararg filters: Pair<String, List<String>>): ConvertedAmount {
        return client.get(NetworkEndpoints.ConvertIngredients) {
            parametersOf(pairs = filters).forEach { s, list ->
                this.url.parameters.appendAll(s, list)
            }
        }
    }

    override suspend fun getRecipesById(ids: List<Long>): List<RecipeEntity> {
        return client.get(NetworkEndpoints.RecipeSearchById) {
            parameter("ids", ids.joinToString(separator = ",") { it.toString() })
        }
    }
}