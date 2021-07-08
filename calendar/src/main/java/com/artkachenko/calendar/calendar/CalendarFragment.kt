package com.artkachenko.calendar.calendar

import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.FragmentCalendarBinding
import com.artkachenko.core_api.base.BaseFragment
import com.artkachenko.core_api.network.models.RecipeEntity
import com.artkachenko.core_api.utils.PrefManager
import com.artkachenko.ui_utils.ID
import com.artkachenko.ui_utils.ImageUtils
import com.artkachenko.ui_utils.TRANSITION_NAME
import com.artkachenko.ui_utils.decorations.MarginItemDecoration
import com.artkachenko.ui_utils.themes.ThemeManager
import com.artkachenko.ui_utils.views.MenuFab
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.utils.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment : BaseFragment(R.layout.fragment_calendar), CalendarActions {

    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var prefManager: PrefManager

    private val viewModel by viewModels<CalendarViewModel>()

    private val progressAdapter by lazy {
        ProgressChartAdapter()
    }

    private val pieAdapter by lazy {
        PieAdapter()
    }

    private val sourcesAdapter by lazy {
        SourcesAdapter()
    }

    private val recipesUsedAdapter by lazy {
        UsedRecipesAdapter(this)
    }

    private val adapter by lazy {
        ConcatAdapter(progressAdapter, pieAdapter, sourcesAdapter, recipesUsedAdapter)
    }

    private lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        with(binding) {
            info.adapter = adapter
            val decoration = MarginItemDecoration(requireContext(), marginLeft = 16, marginRight = 16)

            info.addItemDecoration(decoration)

            generateFabConfigs().forEach {
                menuFab.addFab(it)
            }
        }

        viewModel.getDishes()

        lifecycleScope.launchWhenResumed {
            viewModel.state.collectLatest {
                processState(it)
            }
        }

        val dm = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics(dm)
        } else {
            val display = activity?.windowManager?.defaultDisplay
            display?.getMetrics(dm)
        }
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
        binding.calendar.scrollToDate(viewModel.selectedDate.value.minusDays(3))
    }

    override fun onResume() {
        (activity as ImageUtils.CanHideBottomNavView).showNavigationBar(true)
        super.onResume()
    }

    override fun onItemClicked(model: RecipeEntity, view: View) {
        val bundle = Bundle().apply {
            putLong(ID, model.id)
            putString(TRANSITION_NAME, view.transitionName)
        }

        val extras = FragmentNavigatorExtras(view to "recipeImage")

        findNavController().navigate(R.id.calendar_to_detail, bundle, null, extras)
    }

    override fun changeDate(day: CalendarDay) {
        val date = day.date

        binding.calendar.smoothScrollToDate(day.date.minusDays(3))

        val selectedDate = viewModel.selectedDate.value

        if (selectedDate != date) {
            selectedDate.let { binding.calendar.notifyDateChanged(it) }
            viewModel.changeDate(date)
            binding.calendar.notifyDateChanged(date)
        }
    }

    override fun getDate(): Flow<LocalDate> {
        return viewModel.selectedDate
    }

    private fun processState(state: CalendarViewModel.State) {
        when (state) {
            is CalendarViewModel.State.Bar -> sourcesAdapter.setInitial(listOf(state.data))
            is CalendarViewModel.State.Calories -> {
                progressAdapter.setInitial(listOf(state.data.toLong() to prefManager.desiredCalories.toLong()))
            }
            is CalendarViewModel.State.Dishes -> {
            }
            is CalendarViewModel.State.Pie -> pieAdapter.setInitial(listOf(state.data))

            CalendarViewModel.State.Clear -> clearAdapters()
            CalendarViewModel.State.Visible -> binding.info.isVisible = true
            is CalendarViewModel.State.Recipes -> {
                recipesUsedAdapter.setData(state.data)
            }
            CalendarViewModel.State.Initial -> { }
            CalendarViewModel.State.FinishedLoading -> {}
        }
    }

    private fun clearAdapters() {
        binding.info.isVisible = false
        pieAdapter.clear()
        progressAdapter.clear()
        sourcesAdapter.clear()
        recipesUsedAdapter.clear()
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
            ) {},
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