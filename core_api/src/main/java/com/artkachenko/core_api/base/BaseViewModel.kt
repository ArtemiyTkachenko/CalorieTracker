package com.artkachenko.core_api.base

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface BaseViewModel {

    val parentJob: Job

    val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    val exceptionHandler: CoroutineExceptionHandler

    val scope: CoroutineScope
}