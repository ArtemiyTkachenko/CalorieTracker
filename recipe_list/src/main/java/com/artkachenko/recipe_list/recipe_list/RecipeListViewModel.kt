package com.artkachenko.recipe_list.recipe_list

import BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeEntity>>(mutableListOf())

    private var page = 1

    val recipes: StateFlow<List<RecipeEntity>>
        get() = _recipes

    fun getRecipeList() {
        viewModelScope.launch {
            val results = recipeRepository.getRecipeList(1)
            _recipes.emit(results)
        }
    }
}