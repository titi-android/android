package com.busschedule.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.model.constant.DayOfWeek
import java.time.LocalDate

data class DayOfWeekUi(
    val dayOfWeek: DayOfWeek,
    private val init: Boolean = false,
) {
    var isSelected by mutableStateOf(init)

    fun updateSelected(selected: Boolean) = apply {
        isSelected = selected
    }

    fun isToday() =
        this.dayOfWeek.enName == LocalDate.now().dayOfWeek.name


    fun getDayOfWeeks() = "${dayOfWeek.value}요일"
}
