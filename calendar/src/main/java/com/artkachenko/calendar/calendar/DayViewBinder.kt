package com.artkachenko.calendar.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi

class DayViewBinder(private val bindings: CalendarActions, private val scope: CoroutineScope) :
    DayBinder<DayViewContainer> {
    override fun create(view: View) = DayViewContainer(view, bindings, scope)

    @InternalCoroutinesApi
    override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
}