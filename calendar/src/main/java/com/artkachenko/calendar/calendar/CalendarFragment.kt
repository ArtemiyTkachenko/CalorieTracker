package com.artkachenko.calendar.calendar

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.FragmentCalendarBinding
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.core_api.utils.debugLog
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.themes.ThemeManager
import com.artkachenko.ui_utils.views.MenuFab
import com.github.mikephil.charting.utils.Utils
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.utils.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@InternalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CalendarActions {

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var prefManager: PrefManager

    private val viewModel by viewModels<CalendarViewModel>()

    private val adapter by lazy {
        ChartAdapter(themeManager)
    }

    private lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        Utils.init(requireContext())

        with(binding) {
            info.adapter = adapter

            generateFabConfigs().forEach {
                menuFab.addFab(it)
            }
        }

        viewModel.getDishes()

        scope.launch {
            debugLog("CalendarFragment, scope launched")
            viewModel.state.collect {
                debugLog("CalendarFragment, state is $it")
                processState(it)
            }
        }

        val dm = DisplayMetrics()
        requireContext().display?.getRealMetrics(dm)
        binding.calendar.apply {
            val dayWidth = dm.widthPixels / 7
            val dayHeight = (dayWidth * 1.5).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        binding.calendar.dayBinder = DayViewBinder(this, scope, themeManager)

        val currentMonth = YearMonth.now()
        binding.calendar.setup(
            currentMonth.minusMonths(3),
            currentMonth.plusMonths(3),
            DayOfWeek.MONDAY
        )
        binding.calendar.scrollToDate(LocalDate.now().minusDays(3))
    }

    override fun onResume() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(true)
        super.onResume()
    }

    override fun onItemClicked(model: ManualDishDetail, view: View) {

    }

    override fun changeDate(day: CalendarDay) {
        val date = day.date

        binding.calendar.smoothScrollToDate(day.date.minusDays(3))

        val selectedDate = viewModel.selectedDate.value

        if (selectedDate != date) {
            selectedDate?.let { binding.calendar.notifyDateChanged(it) }
            viewModel.changeDate(date)
            binding.calendar.notifyDateChanged(date)
        }
    }

    override fun getDate(): Flow<LocalDate> {
        return viewModel.selectedDate
    }

    @SuppressLint("SetTextI18n")
    private fun processState(state: CalendarViewModel.State) {
        when (state) {
            is CalendarViewModel.State.Bar -> adapter.addData(ChartDataWrapper(2, state.data))
            is CalendarViewModel.State.Calories -> {
                val desiredAmount = prefManager.desiredCalories
                binding.calorieBase.text = desiredAmount.toString()
                binding.caloriesSpent.text = "${state.data}"
                binding.caloriesLeft.text = "${desiredAmount - state.data}"
            }
            is CalendarViewModel.State.Dishes -> {
            }
            is CalendarViewModel.State.Pie -> adapter.addData(ChartDataWrapper(1, state.data))
            CalendarViewModel.State.Clear -> {
                binding.info.isVisible = false
                adapter.clearList()
            }
            CalendarViewModel.State.Visible -> binding.info.isVisible = true
        }
    }

    private fun generateFabConfigs(): List<MenuFab.FabConfig> {
        val point = Point(56, 56)
        val margins = 16F
        return listOf(
            MenuFab.FabConfig(
                size = point,
                margins = margins,
                icon = R.drawable.ic_food
            ) {
//                val directions = InfoFragmentDirections.infoScreenToAdd(
//                    viewModel.selectedDate.value ?: LocalDate.now()
//                )
//                navigateWithClean(directions)
            },
            MenuFab.FabConfig(
                size = point,
                margins = margins,
                icon = R.drawable.ic_calories
            )
            {},
            MenuFab.FabConfig(
                size = point,
                margins = margins,
                icon = R.drawable.ic_exercise
            ) {
//                val directions = InfoFragmentDirections.infoScreenToExercise()
//                navigate(directions)
            }
        )
    }
}