package com.artkachenko.core_api.network.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artkachenko.core_api.network.models.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise")
    fun getExercises() : Flow<List<Exercise>>
}