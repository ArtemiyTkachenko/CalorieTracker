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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val dishesRepository: DishesRepository,
    private val scopeProvider: ViewModelScopeProvider
) : ViewModel(), ViewModelScopeProvider by scopeProvider {

    val channel = Channel<RecipeDetailModel> { }

    fun getRecipeDetail(id: Long) {
        scope.launch {
            val detail = repository.getRecipeDetail(id)
            debugLog("detail is $detail")
            channel.send(detail)
        }
    }

    fun saveRecipe(model: RecipeDetailModel, servingSize: Int) {

        scope.launch(Dispatchers.IO) {
            val ingredientIncrement = servingSize.toDouble()/(model.servings ?: 1)
            val nutritionIncrement = servingSize.toDouble()

            debugLog("CONVERSION, ingredientIncrement is $ingredientIncrement and nutritionIncrement is $nutritionIncrement")
            val adjustedIngredients = model.extendedIngredients?.map {
                val converted = repository.convertIngredients(
                    "ingredientName" to listOf(it.name ?: ""),
                    "sourceAmount" to listOf((it.amount?.times(ingredientIncrement)).toString() ?: ""),
                    "sourceUnit" to listOf(it.unit ?: ""),
                    "targetUnit" to listOf("grams"),
                )
                debugLog("CONVERSION, converted amount is ${converted.targetAmount} ${converted.targetUnit}, original amount is ${it.amount} ${it.unit} of ${it.name} ")
                it.copy(convertedAmount = converted)
            }
            val nutrition = model.nutrition
            val adjustedNutrition = nutrition?.copy(
                nutrients = nutrition.nutrients?.map { nutritionItem ->
                    nutritionItem.copy(
                        amount = nutritionItem.amount?.times(nutritionIncrement),
                        percentOfDailyNeeds = nutritionItem.percentOfDailyNeeds?.times(nutritionIncrement)
                    )
                },
                properties = nutrition.properties?.map { propertiesItem ->
                    propertiesItem.copy(amount = propertiesItem.amount?.times(nutritionIncrement))
                },
                weightPerServing = nutrition.weightPerServing?.copy(
                    amount = nutrition.weightPerServing?.amount?.times(nutritionIncrement) ?: 0.0
                )
            )

            val manualDish = ManualDishDetail(
                extendedIngredients = adjustedIngredients,
                nutrition = adjustedNutrition,
                date = LocalDateTime.now()
            )
            dishesRepository.insertDish(manualDish)
        }
    }
}