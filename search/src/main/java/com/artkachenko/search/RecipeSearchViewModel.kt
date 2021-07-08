package com.artkachenko.search

import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.FilterItemWrapper
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
        if (isLoading || checkIfEmptySearch(query, wrapper)) return

        scope.launch {
            if (offset == 0) _state.value = State.FirstLoad
            isLoading = true
            val recipes = recipeRepository.getRecipeList(
                offset,
                "query" to listOf(query),
                "offset" to listOf(offset.toString()),
                "addRecipeInformation" to listOf("true"),
                *wrapper?.filters?.map { it.key to it.value.map { it.value } }?.toTypedArray() ?: arrayOf()
            )
            _state.value = State.Success(recipes)
            offset += 10
            isLoading = false
            _state.value = State.LoadingFinished
        }
    }

    fun processFilter(filter: Map.Entry<String, FilterItemWrapper>) {
        if (filtersWrapper == null) filtersWrapper = FilterWrapper(mutableMapOf())
        val filters = filtersWrapper?.filters
        val filterKey = filter.key
        val filterValue = filter.value
        val keyList = filters?.get(filter.key) ?: mutableListOf()
        if (filterValue.isChecked && !keyList.contains(filterValue)) keyList.add(filterValue) else keyList.remove(filterValue)
        filters?.put(filterKey, keyList)
        debugLog("filters are $filters")
    }

    fun setFilter(wrapper: FilterWrapper?) {
        this.filtersWrapper = wrapper?.copy()
    }

    private fun checkIfEmptySearch(query: String, wrapper: FilterWrapper?): Boolean {
        var filterIsEmpty = true
        wrapper?.filters?.values?.forEach {
            if (!it.isNullOrEmpty()) {
                filterIsEmpty = false
                return@forEach
            }
        }
        return query.isEmpty() && filterIsEmpty
    }

    fun setFilter() {
        _state.value = State.FiltersSet
    }

    fun clear() {
        offset = 0
        isLoading = false
        _state.value = State.Initial
        filtersWrapper = null
    }

    sealed class State() {
        object Initial : State()
        object FirstLoad : State()
        class Success(val data: List<RecipeEntity>) : State()
        object LoadingFinished : State()
        object FiltersSet : State()
    }
}