package com.artkachenko.core_api.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.artkachenko.core_api.utils.debugLog
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout), LifecycleObserver {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.addObserver(this)
        super.onCreate(savedInstanceState)
    }

    protected val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        debugLog("ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        debugLog("ON_DESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        debugLog("ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        debugLog("ON_PAUSE")
    }
}