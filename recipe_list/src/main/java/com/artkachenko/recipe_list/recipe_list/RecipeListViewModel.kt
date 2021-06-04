package com.artkachenko.recipe_list.recipe_list

import BaseViewModelImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.base.BaseViewModel
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.core_impl.network.FilterPresets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel(),BaseViewModel by BaseViewModelImpl() {

    private val _recipes = MutableStateFlow<State>(State.Initial)

    private var page = 1

    val recipes: StateFlow<State>
        get() = _recipes

    fun getRecipeList() {
        scope.launch {
            val italianRecipes = recipeRepository.getRecipeList(0, FilterPresets.italianPreset, FilterPresets.fiveItemPreset)
            _recipes.emit(State.Italian(italianRecipes))
            val vegetarianRecipes = recipeRepository.getRecipeList(0, FilterPresets.vegetarianPreset, FilterPresets.fiveItemPreset)
            _recipes.emit(State.Vegetarian(vegetarianRecipes))
            val indianRecipes = recipeRepository.getRecipeList(0, FilterPresets.indianPreset, FilterPresets.fiveItemPreset)
            _recipes.emit(State.Indian(indianRecipes))
            val quickRecipes = recipeRepository.getRecipeList(0, FilterPresets.quickPreset, FilterPresets.fiveItemPreset)
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