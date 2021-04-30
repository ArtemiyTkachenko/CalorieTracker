package com.artkachenko.core_api.network.api

import com.artkachenko.core_api.network.models.RecipeEntity
import io.ktor.client.*
import io.ktor.client.request.*

class RecipeApi(private val client: HttpClient) {

    //    suspend fun getRecipeList(page: Int): Either {
//        return client.requestAndCatch({
//            return@requestAndCatch Right(get<RecipeEntity>(""))
//        }, {
//            return@requestAndCatch Left(this)
//        })
//    }
    suspend fun getRecipeList(page: Int): RecipeEntity {
        return client.get<RecipeEntity>("")
    }
}