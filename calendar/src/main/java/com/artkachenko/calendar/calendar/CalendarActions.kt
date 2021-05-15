package com.artkachenko.calendar.calendar

import com.artkachenko.core_api.base.ViewHolderBindings
import com.artkachenko.core_api.network.models.ManualDishDetail
import com.kizitonwose.calendarview.model.CalendarDay
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CalendarActions: ViewHolderBindings<ManualDishDetail> {
    fun changeDate(day: CalendarDay)

    fun getDate() : Flow<LocalDate>
}