package com.artkachenko.calendar.calendar

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.artkachenko.calendar.R
import com.artkachenko.calendar.databinding.IDayContainerBinding
import com.artkachenko.ui_utils.Formatters
import com.artkachenko.ui_utils.setSingleClickListener
import com.artkachenko.ui_utils.themes.Theme
import com.artkachenko.ui_utils.themes.ThemeManager
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DayViewContainer(
    view: View,
    private val actions: CalendarActions,
    private val scope: CoroutineScope,
    private val themeManager: ThemeManager
) : ViewContainer(view) {

    private val bind = IDayContainerBinding.bind(view)
    private lateinit var day: CalendarDay

    init {
        view.setSingleClickListener {
            actions.changeDate(day)
        }
    }

    @InternalCoroutinesApi
    fun bind(day: CalendarDay) {
        this.day = day

        with(bind) {
            dateText.text = Formatters.dateFormatter.format(day.date)
            exSevenDayText.text = Formatters.dayFormatter.format(day.date)
            exSevenMonthText.text = Formatters.monthFormatter.format(day.date)
        }

        scope.launch {
            actions.getDate().collect { date ->
                val color = when (themeManager.theme) {
                    Theme.DARK -> if (day.date == date) ContextCompat.getColor(view.context, R.color.purple_200) else Color.WHITE
                    Theme.LIGHT -> if (day.date == date) ContextCompat.getColor(view.context, R.color.purple_500) else Color.BLACK
                }
                bind.dateText.setTextColor(color
                )
                bind.dayView.isVisible = day.date == date
            }
        }
    }
}