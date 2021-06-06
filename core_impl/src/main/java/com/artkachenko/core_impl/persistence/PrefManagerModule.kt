package com.artkachenko.core_impl.persistence

import android.app.Application
import android.content.Context
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.core_impl.utils.PrefManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefManagerModule {

    @Provides
    @Singleton
    fun providePrefManager(application: Application): PrefManager {
        val prefs = application.getSharedPreferences("calorieTrackerPrefs", Context.MODE_PRIVATE)
        return PrefManagerImpl(prefs)
    }
}