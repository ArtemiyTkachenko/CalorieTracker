package com.artkachenko.search

import BaseViewModelImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.base.BaseViewModel
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel(), BaseViewModel by BaseViewModelImpl() {

    private var offset = 0

    private var isLoading = false

    val results: SharedFlow<List<RecipeEntity>>
        get() = _results

    private val _results = MutableSharedFlow<List<RecipeEntity>>()

    private var filtersWrapper: FilterWrapper? = null

    fun getInitial(query: String, wrapper: FilterWrapper? = filtersWrapper) {
        offset = 0
        loadRecipes(query, wrapper)
    }

    fun loadRecipes(query: String, wrapper: FilterWrapper? = filtersWrapper) {
        if (isLoading) return

        scope.launch {
            isLoading = true
            val recipes = recipeRepository.getRecipeList(
                offset,
                "query" to listOf(query),
                "offset" to listOf(offset.toString()),
                *wrapper?.filters?.toList()?.toTypedArray() ?: arrayOf()
            )
            debugLog("results from viewmodel getRecipeList are $recipes")
            _results.emit(recipes)
            offset += 10
            isLoading = false
        }
    }

    sealed class State() {
        object Initial : State()
        object Loading : State()
        object FirstItemEmitted : State()
        object LoadingFinished : State()
    }
}