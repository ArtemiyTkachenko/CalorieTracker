package com.artkachenko.core_api.network.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.artkachenko.core_api.network.models.Ingredient

@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(model: Ingredient)
}