package com.artkachenko.recipe_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val dishesRepository: DishesRepository
    ) : ViewModel() {

    val channel = Channel<RecipeDetailModel> {  }

    fun getRecipeDetail(id: Long) {
        viewModelScope.launch {
            val detail = repository.getRecipeDetail(id)
            debugLog("detail is $detail")
            channel.send(detail)
        }
    }

    fun saveRecipe(model: RecipeDetailModel) {
        val manualDish = ManualDishDetail(
            extendedIngredients = model.extendedIngredients,
            nutrition = model.nutrition,
            date = LocalDateTime.now())
        viewModelScope.launch {
            dishesRepository.insertDish(manualDish)
        }
    }
}