package com.artkachenko.calendar.calendar

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import com.artkachenko.calendar.databinding.IDayContainerBinding
import com.artkachenko.ui_utils.Formatters
import com.artkachenko.ui_utils.setSingleClickListener
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DayViewContainer(
    view: View,
    private val actions: CalendarActions,
    private val scope: CoroutineScope
) : ViewContainer(view) {

    val bind = IDayContainerBinding.bind(view)
    private lateinit var day: CalendarDay

    init {
        view.setSingleClickListener {
            actions.changeDate(day)
        }
    }

    @InternalCoroutinesApi
    fun bind(day: CalendarDay) {
        this.day = day

        with (bind) {
            dateText.text = Formatters.dateFormatter.format(day.date)
            exSevenDayText.text = Formatters.dayFormatter.format(day.date)
            exSevenMonthText.text = Formatters.monthFormatter.format(day.date)
        }

        scope.launch {
            actions.getDate().collect { date ->
                bind.dateText.setTextColor(if (day.date == date) Color.BLUE else Color.BLACK)
                bind.dayView.isVisible = day.date == date
            }
        }
    }
}