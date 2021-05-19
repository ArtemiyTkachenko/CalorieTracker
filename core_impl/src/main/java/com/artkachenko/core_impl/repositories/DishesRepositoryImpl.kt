package com.artkachenko.core_impl.repositories

import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.persistence.DishesDao
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_impl.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class DishesRepositoryImpl @Inject constructor(
    private val dishesDao: DishesDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : DishesRepository {

    override suspend fun insertDish(dish: ManualDishDetail) =
        withContext(dispatcher) {
            dishesDao.addDish(dish)
        }

    override suspend fun getDishes() = withContext(dispatcher) {
        dishesDao.getDishes()
    }

    override suspend fun getDishesByDate(
        start: LocalDateTime,
        end: LocalDateTime
    ) = withContext(dispatcher) {
        dishesDao.getDishesByDate(start, end)
    }
}