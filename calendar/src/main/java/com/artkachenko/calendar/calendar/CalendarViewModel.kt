package com.artkachenko.calendar.calendar

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artkachenko.core_api.network.models.IngredientTitles
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.network.repositories.DishesRepository
import com.artkachenko.core_api.utils.debugLog
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val dishesRepository: DishesRepository
) : ViewModel() {

    val selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())

    val state = MutableSharedFlow<State>()

    fun changeDate(date: LocalDate) {
        viewModelScope.launch {
            selectedDate.emit(date)
            val start = date.atStartOfDay()
            val end = date.atStartOfDay().plusDays(1)
            state.emit(State.Gone)
            getDishes(start, end)
        }
    }

    fun getDishes(
        start: LocalDateTime = LocalDate.now().atStartOfDay(),
        end: LocalDateTime = LocalDate.now().atStartOfDay().plusDays(1)
    ) {
        viewModelScope.launch {
            dishesRepository.getDishesByDate(start, end).collect { list ->
                state.emit(State.Dishes(list))
                val fatItems = mutableListOf<Double>()
                val proteinItems = mutableListOf<Double>()
                val carbItems = mutableListOf<Double>()
                val sources = mutableMapOf<String, Double>()
                var calories = 0.0
                var totalWeight = 0.0
                list.forEach { dishDetail ->
                    dishDetail.extendedIngredients?.forEach { ingredient ->
                        totalWeight += ingredient.amount ?: 0.0

                        val formattedTitle = ingredient.aisle?.replace("Frozen;", "") ?: ""

                        val previousValue = sources[formattedTitle] ?: 0.0

                        sources[formattedTitle] = previousValue.plus(ingredient.amount ?: 0.0)
                        calories += ingredient.nutrition?.nutrients?.firstOrNull {
                            IngredientTitles.CALORIES == IngredientTitles.mapFromString(
                                it.title
                            )
                        }?.amount ?: 0.0
                    }

                    val breakdown = dishDetail.nutrition?.caloricBreakdown

                    breakdown?.percentFat?.let { fatItems.add(it) }
                    breakdown?.percentProtein?.let { proteinItems.add(it) }
                    breakdown?.percentCarbs?.let { carbItems.add(it) }
                }

                val fatAverage = fatItems.average()
                val proteinAverage = proteinItems.average()
                val carbAverage = carbItems.average()

                if (!sources.isNullOrEmpty()) {
                    var count = 0
                    val entries = mutableListOf<BarEntry>()
                    val labels = mutableListOf<String>()
                    sources.forEach {
                        entries.add(BarEntry(count.toFloat(), it.value.toFloat()))
                        labels.add(it.key)
                        count++
                    }

                    val dataSet = BarDataSet(entries, "")
                    dataSet.stackLabels = labels.toTypedArray()
                    dataSet.colors = ColorTemplate.COLORFUL_COLORS.toMutableList()

                    state.emit(State.Bar(BarData(dataSet)))
                    state.emit(State.Visible)
                }

                if (!fatAverage.isNaN() || !proteinAverage.isNaN() || !carbAverage.isNaN()) {

                    val entries = mutableListOf<PieEntry>().apply {
                        add(PieEntry(fatAverage.toFloat(), "Fat"))
                        add(PieEntry(proteinAverage.toFloat(), "Protein"))
                        add(PieEntry(carbAverage.toFloat(), "Carbs"))
                    }

                    val dataSet = PieDataSet(entries, "")

                    dataSet.apply {
                        sliceSpace = 2F
                        colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
                        valueTextSize = 10F
                        valueTextColor = Color.WHITE
                    }

                    state.emit(State.Pie(PieData(dataSet)))
                    state.emit(State.Visible)
                }
                state.emit(State.Calories(calories))
            }
        }
    }

    sealed class State() {
        data class Pie(val data: PieData) : State()
        data class Bar(val data: BarData) : State()
        data class Calories(val data: Double) : State()
        data class Dishes(val data: List<ManualDishDetail>?) : State()
        object Visible : State()
        object Gone : State()
    }
}