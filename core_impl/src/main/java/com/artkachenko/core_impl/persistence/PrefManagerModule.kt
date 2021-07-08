package com.artkachenko.core_impl.persistence

import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.core_impl.utils.PrefManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PrefManagerModule {

    @Binds
    abstract fun providePrefManager(prefManagerImpl: PrefManagerImpl): PrefManager
}