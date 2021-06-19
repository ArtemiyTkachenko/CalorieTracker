package com.artkachenko.ui_utils.themes

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface ViewScopeProvider {

    val parentJob: Job

    val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    val exceptionHandler: CoroutineExceptionHandler

    val scope: CoroutineScope
}