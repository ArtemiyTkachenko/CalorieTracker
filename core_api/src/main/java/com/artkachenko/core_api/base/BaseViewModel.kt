package com.artkachenko.core_api.base

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface BaseViewModel {
    val parentJob: Job

    val coroutineContext: CoroutineContext

    val exceptionHandler: CoroutineExceptionHandler

    val scope: CoroutineScope
}