@file:Suppress("IllegalIdentifier")

package com.artkachenko.core_impl

import androidx.annotation.CallSuper
import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.persistence.DishesDao
import com.artkachenko.core_impl.repositories.DishesRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class DishesRepositoryTest {

    @MockK
    lateinit var dishesDao: DishesDao

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var dishesRepositoryImpl: DishesRepositoryImpl

    private val details = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<RecipeDetailModel>(Json.serializersModule.serializer(), mockDetail)

    private val list = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<List<RecipeEntity>>(Json.serializersModule.serializer(), mockResults)

    private val ingredients = Json {
        ignoreUnknownKeys = true
    }.decodeFromString<List<Ingredient>>(Json.serializersModule.serializer(), mockIngredients)

    private val mockList = listOf(manualDishMock)

    @Before
    @CallSuper
    fun setUp() {
        MockKAnnotations.init(this)

        dishesRepositoryImpl = DishesRepositoryImpl(dishesDao, dispatcher)

        coEvery {
            dishesDao.getDishes()
        } returns flow {
            emit(mockList)
        }

        coEvery {
            dishesDao.getDishesByDate(any(), any())
        } returns flow {
            emit(mockList)
        }

        coEvery {
            dishesDao.addDish(any())
        } returns Unit
    }

    @Test
    fun `dao called when repository insertDish() is called`() = runBlockingTest {
        dishesRepositoryImpl.insertDish(manualDishMock)

        coVerify {
            dishesDao.addDish(manualDishMock)
        }

        confirmVerified(dishesDao)
    }

    @Test
    fun `dao returns expected data when repository getDishes() is called`() = runBlockingTest {
        val result = dishesDao.getDishes().first()
        assertEquals(result, mockList)
    }

    @Test
    fun `dao returns expected data when repository getDishesByDate() is called`() = runBlockingTest {
        val result = dishesDao.getDishesByDate(LocalDateTime.now(), LocalDateTime.now().plusDays(1)).first()
        assertEquals(result, mockList)
    }
}