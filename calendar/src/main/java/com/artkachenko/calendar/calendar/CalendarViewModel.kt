package com.artkachenko.calendar.calendar

import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.ViewModelScopeProvider
import com.artkachenko.core_api.network.models.IngredientTitles
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.network.repositories.RecipeRepository
import com.artkachenko.core_api.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dishesRepository: DishesRepository,
    private val scopeProvider: ViewModelScopeProvider
) : ViewModel(), ViewModelScopeProvider by scopeProvider {

    val selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())

    val state: SharedFlow<State>
        get() = _state

    private val _state = MutableSharedFlow<State>()

    fun changeDate(date: LocalDate) {
        scope.launch {
            selectedDate.emit(date)
            val start = date.atStartOfDay()
            val end = date.atStartOfDay().plusDays(1)
            _state.emit(State.Clear)
            getDishes(start, end)
        }
    }

    fun getDishes(
        start: LocalDateTime = selectedDate.value.atStartOfDay(),
        end: LocalDateTime = selectedDate.value.atStartOfDay().plusDays(1)
    ) {
        debugLog("USEDRECIPE, model at getDishes")

        scope.launch {
            dishesRepository.getDishesByDate(start, end).collect { list ->
                debugLog("USEDRECIPE, dish list size is ${list.size} ")
                _state.emit(State.Dishes(list))
                val fatItems = mutableListOf<Double>()
                val proteinItems = mutableListOf<Double>()
                val carbItems = mutableListOf<Double>()
                val ingredientsAmount = mutableMapOf<String, Double>()
                var calories = 0
                var totalWeight = 0.0
                val recipeIdsList = mutableListOf<Long>()

                list.forEach { dishDetail ->
                    recipeIdsList.add(dishDetail.recipeId)
                    dishDetail.extendedIngredients?.forEach { ingredient ->
                        totalWeight += ingredient.amount ?: 0.0

                        val title = ingredient.name ?: ""

                        val previousValue = ingredientsAmount[title] ?: 0.0

                        val converted = ingredient.convertedAmount

                        debugLog("CONVERSION, converted amount is ${converted?.answer}")

                        ingredientsAmount[title] = previousValue.plus(ingredient.convertedAmount?.targetAmount ?: 0.0)

                        debugLog("CONVERSION, amount after addition is ${ingredientsAmount[title]} and key is $title")
                    }

                    val breakdown = dishDetail.nutrition?.caloricBreakdown

                    breakdown?.percentFat?.let { fatItems.add(it) }
                    breakdown?.percentProtein?.let { proteinItems.add(it) }
                    breakdown?.percentCarbs?.let { carbItems.add(it) }
                    dishDetail.nutrition?.nutrients?.firstOrNull { it.title == IngredientTitles.CALORIES.title }
                        .let { calories += it?.amount?.toInt() ?: 0 }
                }
                val fatAverage = fatItems.average()
                val proteinAverage = proteinItems.average()
                val carbAverage = carbItems.average()

                emitBarDataSet(ingredientsAmount)

                emitPieDataSet(fatAverage, proteinAverage, carbAverage)

                _state.emit(State.Calories(calories))

                if (!recipeIdsList.isNullOrEmpty()) {
                    val entities = recipeRepository.getRecipesById(recipeIdsList)
                    _state.emit(State.Recipes(entities))
                }

                delay(100)
                _state.emit(State.Initial)
            }
        }
    }

    private suspend fun emitBarDataSet(sources: MutableMap<String, Double>) {
        if (!sources.isNullOrEmpty()) {
            _state.emit(State.Bar(sources))
            _state.emit(State.Visible)
        }
    }

    private suspend fun emitPieDataSet(
        fatAverage: Double,
        proteinAverage: Double,
        carbAverage: Double
    ) {
        if (!fatAverage.isNaN() || !proteinAverage.isNaN() || !carbAverage.isNaN()) {
            _state.emit(State.Pie(Triple(fatAverage.toLong(), proteinAverage.toLong(), carbAverage.toLong())))
            _state.emit(State.Visible)
        }
    }

    sealed class State() {
        object Initial : State()
        data class Pie(val data: Triple<Long, Long, Long>) : State()
        data class Bar(val data: Map<String, Double>) : State()
        data class Calories(val data: Int) : State()
        data class Dishes(val data: List<ManualDishDetail>?) : State()
        data class Recipes(val data: List<RecipeEntity>) : State()
        object Visible : State()
        object Clear : State()
    }
}