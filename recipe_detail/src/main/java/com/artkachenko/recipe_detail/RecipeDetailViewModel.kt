package com.artkachenko.recipe_detail

import BaseViewModelImpl
import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.BaseViewModel
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.models.RecipeDetailModel
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val dishesRepository: DishesRepository
    ) : ViewModel(), BaseViewModel by BaseViewModelImpl() {

    val channel = Channel<RecipeDetailModel> {  }

    fun getRecipeDetail(id: Long) {
        scope.launch {
            val detail = repository.getRecipeDetail(id)
            debugLog("detail is $detail")
            channel.send(detail)
        }
    }

    fun saveRecipe(model: RecipeDetailModel, servingSize: Int) {
        val servings = model.servings ?: 0
        val increment = servingSize/servings
        val manualDish = ManualDishDetail(
            extendedIngredients = model.extendedIngredients,
            nutrition = model.nutrition,
            date = LocalDateTime.now())
        scope.launch {
            dishesRepository.insertDish(manualDish)
        }
    }
}