package com.artkachenko.ui_utils.themes

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BaseCoroutineViewImpl : BaseCoroutineView {

    override val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    override val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)

}