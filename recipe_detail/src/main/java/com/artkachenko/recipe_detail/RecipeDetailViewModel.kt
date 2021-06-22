package com.artkachenko.recipe_detail

import com.artkachenko.core_impl.viewmodel.ViewModelScopeProviderImpl
import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.ViewModelScopeProvider
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
    private val dishesRepository: DishesRepository,
    private val scopeProvider: ViewModelScopeProvider
    ) : ViewModel(), ViewModelScopeProvider by scopeProvider {

    val channel = Channel<RecipeDetailModel> {  }

    fun getRecipeDetail(id: Long) {
        scope.launch {
            val detail = repository.getRecipeDetail(id)
            debugLog("detail is $detail")
            channel.send(detail)
        }
    }

    fun saveRecipe(model: RecipeDetailModel, servingSize: Int) {
        val increment = servingSize.toDouble()
        val adjustedIngredients = model.extendedIngredients?.map {
            it.copy(amount = it.amount?.times(increment))
        }
        val nutrition = model.nutrition
        val adjustedNutrition = nutrition?.copy(
            nutrients = nutrition.nutrients?.map { nutritionItem ->
                nutritionItem.copy(
                    amount = nutritionItem.amount?.times(increment),
                    percentOfDailyNeeds = nutritionItem.percentOfDailyNeeds?.times(increment)
                )
            },
            properties = nutrition.properties?.map { propertiesItem ->
                propertiesItem.copy(amount = propertiesItem.amount?.times(increment))
            },
            weightPerServing = nutrition.weightPerServing?.copy(
                amount = nutrition.weightPerServing?.amount?.times(increment) ?: 0.0
            )
        )

        debugLog("nutrition is $adjustedNutrition")
        debugLog("ingredients are $adjustedIngredients")

        val manualDish = ManualDishDetail(
            extendedIngredients = adjustedIngredients,
            nutrition = adjustedNutrition,
            date = LocalDateTime.now())
        scope.launch {
            dishesRepository.insertDish(manualDish)
        }
    }
}