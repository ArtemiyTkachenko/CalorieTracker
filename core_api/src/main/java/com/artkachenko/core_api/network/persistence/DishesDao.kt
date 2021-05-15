package com.artkachenko.core_api.network.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artkachenko.core_api.network.models.ManualDishDetail
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface DishesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDish(manualDishDetail: ManualDishDetail)

    @Query("SELECT * FROM manual_dishes")
    fun getDishes() : Flow<List<ManualDishDetail>>

    @Query("SELECT * FROM manual_dishes WHERE date BETWEEN :start AND :end")
    fun getDishesByDate(start: LocalDateTime, end: LocalDateTime) : Flow<List<ManualDishDetail>>
}