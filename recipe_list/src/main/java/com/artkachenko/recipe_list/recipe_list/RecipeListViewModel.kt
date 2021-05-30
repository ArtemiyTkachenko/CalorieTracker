package com.artkachenko.recipe_list.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableStateFlow<State>(State.Initial)

    private var page = 1

    val recipes: StateFlow<State>
        get() = _recipes

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    protected val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)


    fun getRecipeList() {
        scope.launch {
            val italianRecipes = recipeRepository.getRecipeList(1, "cuisine" to listOf("italian"), "number" to listOf("5"))
            _recipes.emit(State.Italian(italianRecipes))
            val vegetarianRecipes = recipeRepository.getRecipeList(1, "diet" to listOf("vegetarian"), "number" to listOf("5"))
            _recipes.emit(State.Vegetarian(vegetarianRecipes))
            val indianRecipes = recipeRepository.getRecipeList(1, "cuisine" to listOf("indian"), "number" to listOf("5"))
            _recipes.emit(State.Indian(indianRecipes))
            val quickRecipes = recipeRepository.getRecipeList(1, "maxReadyTime" to listOf("20"), "number" to listOf("5"))
            _recipes.emit(State.Quick(quickRecipes))
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