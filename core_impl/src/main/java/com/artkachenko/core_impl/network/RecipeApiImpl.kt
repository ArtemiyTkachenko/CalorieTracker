package com.artkachenko.core_impl.network

import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.models.RecipeResultsWrapper
import com.artkachenko.core_api.utils.debugLog
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeApiImpl @Inject constructor(private val client: HttpClient) : RecipeApi {

    override suspend fun getRecipeList(page: Int): List<RecipeEntity> {
        val wrapper = client.get<RecipeResultsWrapper>(NetworkEndpoints.RecepeSearch) {
//            url.path("/recipes/search")
            parameter("query", "chicken")
        }
        debugLog("results are ${wrapper.results}")
        return wrapper.results
//        return emptyList()
    }

    override suspend fun getRecipeDetail(id: Int): RecipeEntity {
        return client.get("")
    }
}