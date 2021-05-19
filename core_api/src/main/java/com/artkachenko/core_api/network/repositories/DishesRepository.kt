package com.artkachenko.core_api.network.repositories

import com.artkachenko.core_api.network.models.ManualDishDetail
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface DishesRepository {
    suspend fun insertDish(dish: ManualDishDetail)

    suspend fun getDishes(): Flow<List<ManualDishDetail>>

    suspend fun getDishesByDate(start: LocalDateTime, end: LocalDateTime) : Flow<List<ManualDishDetail>>
}