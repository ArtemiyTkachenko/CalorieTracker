package com.artkachenko.core_impl.network

import android.app.Application
import androidx.room.Room
import com.artkachenko.core_api.network.persistence.DB
import com.artkachenko.core_api.network.persistence.DishesDao
import com.artkachenko.core_api.network.persistence.IngredientsDao
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_impl.repositories.DishesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): DB {
        return Room
            .databaseBuilder(application, DB::class.java, "calorie_tracker.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDishesRepository(recipeRepository: RecipeRepository, dishesDao: DishesDao) : DishesRepository {
        return DishesRepositoryImpl(recipeRepository, dishesDao)
    }

    @Provides
    @Singleton
    fun provideIngredientsDao(appDatabase: DB) : IngredientsDao {
        return appDatabase.ingredientsDao()
    }

    @Provides
    @Singleton
    fun provideDishesDao(appDatabase: DB) : DishesDao {
        return appDatabase.dishesDao()
    }
}