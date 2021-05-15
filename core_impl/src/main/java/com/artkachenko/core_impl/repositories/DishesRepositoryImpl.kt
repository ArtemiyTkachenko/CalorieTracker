package com.artkachenko.core_impl.repositories

import com.artkachenko.core_api.network.models.Ingredient
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.persistence.DishesDao
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.network.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class DishesRepositoryImpl @Inject constructor (private val recipeRepository: RecipeRepository, private val dishesDao: DishesDao): DishesRepository {
    override suspend fun parseIngredients(ingredients: List<String>): List<Ingredient> {
        return recipeRepository.parseIngredients(ingredients)
    }

    override suspend fun insertDish(dish: ManualDishDetail) {
        dishesDao.addDish(dish)
    }

    override suspend fun getDishes(): Flow<List<ManualDishDetail>> {
        return dishesDao.getDishes()
    }

    override suspend fun getDishesByDate(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<ManualDishDetail>> {
        return dishesDao.getDishesByDate(start, end)
    }
}