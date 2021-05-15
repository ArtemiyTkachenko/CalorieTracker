package com.artkachenko.recipe_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    val channel = Channel<RecipeDetailModel> {  }

    fun getRecipeDetail(id: Long) {
        viewModelScope.launch {
            val detail = repository.getRecipeDetail(id)
            debugLog("detail is $detail")
            channel.send(detail)
        }
    }
}