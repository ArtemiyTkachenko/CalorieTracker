package com.artkachenko.recipe_list.recipe_list

import com.artkachenko.core_impl.viewmodel.ViewModelScopeProviderImpl
import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_impl.network.Filters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val scopeProvider: ViewModelScopeProvider
    ) : ViewModel(),
    ViewModelScopeProvider by scopeProvider {

    private val _recipes = MutableStateFlow<State>(State.Initial)

    private var page = 1

    val recipes: StateFlow<State>
        get() = _recipes

    fun getRecipeList() {
        scope.launch {
            val italianRecipes = recipeRepository.getRecipeList(0, *Filters.italianPreset.toList().toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Italian(italianRecipes))
            val vegetarianRecipes = recipeRepository.getRecipeList(0, *Filters.vegetarianPreset.toList().toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Vegetarian(vegetarianRecipes))
            val indianRecipes = recipeRepository.getRecipeList(0, *Filters.indianPreset.toList().toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Indian(indianRecipes))
            val quickRecipes = recipeRepository.getRecipeList(0, *Filters.quickPreset.toList().toTypedArray(), Filters.fiveItemPreset)
            _recipes.emit(State.Quick(quickRecipes))
            delay(100)
            _recipes.emit(State.LoadingFinished)
        }
    }

    sealed class State() {
        object Initial: State()
        object Loading: State()
        object FirstItemEmitted: State()
        object LoadingFinished: State()
        class Italian(val data: List<RecipeEntity>) : State()
        class Vegetarian(val data: List<RecipeEntity>) : State()
        class Indian(val data: List<RecipeEntity>) : State()
        class Quick(val data: List<RecipeEntity>) : State()
    }
}