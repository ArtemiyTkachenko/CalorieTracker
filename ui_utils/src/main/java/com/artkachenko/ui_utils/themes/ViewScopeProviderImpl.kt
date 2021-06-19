package com.artkachenko.ui_utils.themes

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ViewScopeProviderImpl : ViewScopeProvider {

    override val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main.immediate

    override val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)

}