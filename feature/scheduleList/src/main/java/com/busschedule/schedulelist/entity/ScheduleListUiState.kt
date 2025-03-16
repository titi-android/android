package com.busschedule.schedulelist.entity

import com.busschedule.model.constant.DayOfWeek
import com.busschedule.model.DayOfWeekUi
import java.time.LocalDate

data class ScheduleListUiState(
    val dayOfWeeks: List<DayOfWeekUi> = DayOfWeek.entries.map {
        DayOfWeekUi(
            dayOfWeek = it,
            init = LocalDate.now().dayOfWeek.name == it.enName
        )
    },
    val schedules: List<ScheduleUI> = emptyList(),
    var isLoading: Boolean = true
) {
    fun getSelectedDayOfWeek(): String =
        dayOfWeeks.find { it.isSelected }?.getDayOfWeeks() ?: "월요일"

}
