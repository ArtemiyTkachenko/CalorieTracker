package com.artkachenko.ui_utils.themes

import com.artkachenko.core_api.utils.PrefManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ThemeManager @Inject constructor(private val prefManager: PrefManager) {
    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)

    var theme = getChosenTheme()
        set(value) {
            field = value
            scope.launch {
                _themeFlow.emit(value)
            }
        }

    init {
        theme = getChosenTheme()
    }

    private fun getChosenTheme(): Theme {
        return if (prefManager.isDarkTheme) Theme.DARK else Theme.LIGHT
    }

    companion object {
        val themeFlow: StateFlow<Theme>
            get() = _themeFlow

        private val _themeFlow = MutableStateFlow(Theme.LIGHT)

    }
}