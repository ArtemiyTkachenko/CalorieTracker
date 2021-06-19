package com.artkachenko.core_impl.viewmodel

import com.artkachenko.core_api.base.ViewModelScopeProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ViewModelScopeProviderImpl: ViewModelScopeProvider {

    override val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main.immediate

    override val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)
}