package com.artkachenko.calendar.calendar

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.FragmentCalendarBinding
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.artkachenko.ui_utils.views.MenuFab
import com.github.mikephil.charting.utils.Utils
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.utils.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@InternalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CalendarActions {

    private val viewModel by viewModels<CalendarViewModel>()

    private val adapter by lazy {
        ChartAdapter()
    }

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater, container, false)

//        hideNavigationBar(false)


        // initialize the utilities
        Utils.init(requireContext())

        with(binding) {
            info.adapter = adapter

            generateFabConfigs().forEach {
                menuFab.addFab(it)
            }
        }

//        viewModel.getDishes()
//
//        observe(viewModel.pieData) {
//            it?.let {
//                adapter.addData(ChartDataWrapper(1, it)) }
//        }
//
//        observe(viewModel.sourcesData) {
//            it?.let {
//                adapter.addData(ChartDataWrapper(2, it))
//            }
//        }
//
//        observe(viewModel.calorieData) {
//            binding.calorieCount.text = "Calorie Count: 2000 - ${it} = ${2000 - it}"
//        }
//
//        observe(viewModel.visibility) {
//            when (it) {
//                InfoViewModel.Visibility.Visible -> binding.info.isVisible = true
//                InfoViewModel.Visibility.Gone -> {
//                    binding.info.isGone = true
//                    adapter.clearList()
//                }
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.calendar.apply {
            val dayWidth = dm.widthPixels / 7
            val dayHeight = (dayWidth * 1.5).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        binding.calendar.dayBinder = DayViewBinder(this, scope)

        val currentMonth = YearMonth.now()
        // Value for firstDayOfWeek does not matter since inDates and outDates are not generated.
        binding.calendar.setup(
            currentMonth,
            currentMonth.plusMonths(3),
            DayOfWeek.values().random()
        )
        binding.calendar.scrollToDate(LocalDate.now())
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
                icon = R.drawable.ic_calories
            ) {
//                val directions = InfoFragmentDirections.infoScreenToExercise()
//                navigate(directions)
            }
        )
    }
//
//    private fun navigateWithClean(navDirections: NavDirections, navOptions: NavOptions? = null) {
//        adapter.clearList()
//        super.navigate(navDirections, navOptions)
//    }
}