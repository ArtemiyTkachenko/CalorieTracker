package com.artkachenko.calendar.calendar

import com.kizitonwose.calendarview.model.CalendarDay
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CalendarActions {
    fun changeDate(day: CalendarDay)

    fun getDate(): Flow<LocalDate>
}