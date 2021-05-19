@file:Suppress("IllegalIdentifier")

package com.artkachenko.core_impl

import androidx.annotation.CallSuper
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.models.RecipeResultsWrapper
import com.artkachenko.core_impl.network.NetworkEndpoints
import com.artkachenko.core_impl.network.RecipeApiImpl
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RecipeApiTest {

    val dispatcher = TestCoroutineDispatcher()

    lateinit var recipeApi: RecipeApiImpl

    private val list = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<List<RecipeEntity>>(Json.serializersModule.serializer(), mockResults)

    private val detail = Json { ignoreUnknownKeys = true }
        .decodeFromString<RecipeDetailModel>(Json.serializersModule.serializer(), mockDetail)

    private val wrapper = RecipeResultsWrapper(list)

    val httpClient: HttpClient = HttpClient(MockEngine) {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                prettyPrint = true
                isLenient = true
            }
            serializer = KotlinxSerializer(json)
        }
        engine {
            addHandler { request ->
                val uri = request.url.toURI().toString()
                when  {
                    uri.contains("recipes/search") -> {
                        respond("{results: $mockResults}", headers = headersOf("Content-Type", ContentType.Application.Json.toString()))
                    }
                    uri.contains(NetworkEndpoints.getRecipeDetailEndPoint(1)) -> {
                        respond(mockDetail, headers = headersOf("Content-Type", ContentType.Application.Json.toString()))
                    }
                    else -> error("request is ${request.url} Unhandled kek")
                }
            }
        }
    }

    @Before
    @CallSuper
    fun setUp() {
        MockKAnnotations.init(this)

        recipeApi = RecipeApiImpl(httpClient)

//        coEvery {
//            httpClient.get<RecipeResultsWrapper>(NetworkEndpoints.RecipeSearch)
//        } returns wrapper
//
//        coEvery {
//            httpClient.get<RecipeDetailModel>(NetworkEndpoints.getRecipeDetailEndPoint(any()))
//        } returns detail
    }

    @Test
    fun `http client returns correct detail when recipeApiImpl getRecipeList() is called`() = runBlocking {
        val result = recipeApi.getRecipeDetail(1)
        assertEquals(result, detail)
    }

    @Test
    fun `http client returns correct list when recipeApiImpl getRecipeList() is called`() = runBlocking {
        val result = recipeApi.getRecipeList(1)
        assertEquals(result, wrapper.results)
    }
}