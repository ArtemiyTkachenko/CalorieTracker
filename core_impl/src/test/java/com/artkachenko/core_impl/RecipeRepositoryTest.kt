@file:Suppress("IllegalIdentifier")

package com.artkachenko.core_impl

import androidx.annotation.CallSuper
import com.artkachenko.core_api.network.api.RecipeApi
import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_impl.repositories.RecipeRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RecipeRepositoryTest {

    @MockK
    lateinit var recipeApi: RecipeApi

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var recipeRepository: RecipeRepositoryImpl

    private val details = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<RecipeDetailModel>(Json.serializersModule.serializer(), mockDetail)

    private val list = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<List<RecipeEntity>>(Json.serializersModule.serializer(), mockResults)

    private val ingredients = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<List<Ingredient>>(Json.serializersModule.serializer(), mockIngredients)

    @Before
    @CallSuper
    fun setUp() {
        MockKAnnotations.init(this)

        recipeRepository = RecipeRepositoryImpl(recipeApi, dispatcher)

        coEvery {
            recipeApi.getRecipeDetail(any())
        } returns details

        coEvery {
            recipeApi.getRecipeList(any())
        } returns list

        coEvery {
            recipeApi.parseIngredients(any())
        } returns ingredients
    }

    @Test
    fun `should return recipe details on recipeRepository getRecipeDetail call`() = runBlockingTest {
        val result = recipeRepository.getRecipeDetail(1)
        assertEquals(result, details)
    }

    @Test
    fun `should return recipe details on recipeRepository getRecipeList call`() = runBlockingTest {
        val result = recipeRepository.getRecipeList(1, "" to emptyList())
        assertEquals(result, list)
    }

    @Test
    fun `should return recipe ingredients on recipeRepository parseIngredients call`() = runBlockingTest {
        val result = recipeRepository.parseIngredients(listOf())
        assertEquals(result, ingredients)
    }
}