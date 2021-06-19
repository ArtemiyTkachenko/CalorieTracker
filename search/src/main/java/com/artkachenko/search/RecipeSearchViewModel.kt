package com.artkachenko.search

import com.artkachenko.core_impl.viewmodel.ViewModelScopeProviderImpl
import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val scopeProvider: ViewModelScopeProvider
    ) :
    ViewModel(), ViewModelScopeProvider by scopeProvider {

    private var offset = 0

    private var isLoading = false

    val state: StateFlow<State>
        get() = _state

    private val _state = MutableStateFlow<State>(State.Initial)

    var filtersWrapper: FilterWrapper? = null
    private set

    fun getInitial(query: String, wrapper: FilterWrapper? = filtersWrapper) {
        offset = 0
        loadRecipes(query, wrapper)
    }

    fun loadRecipes(query: String, wrapper: FilterWrapper? = filtersWrapper) {
        if (isLoading) return

        scope.launch {
            if (offset == 0) _state.emit(State.Loading)
            isLoading = true
            val recipes = recipeRepository.getRecipeList(
                offset,
                "query" to listOf(query),
                "offset" to listOf(offset.toString()),
                *wrapper?.filters?.toList()?.toTypedArray() ?: arrayOf()
            )
            _state.tryEmit(State.Success(recipes))
            offset += 10
            isLoading = false
        }
    }

    fun processFilter(filter: Map.Entry<String, String>, isChecked: Boolean) {
        if (filtersWrapper == null) filtersWrapper = FilterWrapper(mutableMapOf())
        val filters = filtersWrapper?.filters
        val filterKey = filter.key
        val keyList = filters?.get(filter.key) ?: mutableListOf()
        if (isChecked) keyList.add(filter.value) else keyList.remove(filter.value)
        filters?.put(filterKey, keyList)
    }

    sealed class State() {
        object Initial : State()
        object Loading : State()
        class Success(val data: List<RecipeEntity>) : State()
        object LoadingFinished : State()
    }
}