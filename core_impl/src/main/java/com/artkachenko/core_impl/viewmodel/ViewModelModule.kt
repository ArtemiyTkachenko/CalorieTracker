package com.artkachenko.core_impl.viewmodel

import com.artkachenko.core_api.base.ViewModelScopeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    fun provideViewModelScope() : ViewModelScopeProvider {
        return ViewModelScopeProviderImpl()
    }
}