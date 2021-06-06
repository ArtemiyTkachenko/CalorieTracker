package com.artkachenko.core_impl.persistence

import android.app.Application
import androidx.room.Room
import com.artkachenko.core_api.network.persistence.DB
import com.artkachenko.core_api.network.persistence.DishesDao
import com.artkachenko.core_api.network.persistence.IngredientsDao
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_impl.IoDispatcher
import com.artkachenko.core_impl.repositories.DishesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideDishesRepository(dishesDao: DishesDao, @IoDispatcher dispatcher: CoroutineDispatcher) : DishesRepository {
        return DishesRepositoryImpl(dishesDao, dispatcher)
    }

    @Provides
    fun provideIngredientsDao(appDatabase: DB) : IngredientsDao {
        return appDatabase.ingredientsDao()
    }

    @Provides
    fun provideDishesDao(appDatabase: DB) : DishesDao {
        return appDatabase.dishesDao()
    }
}