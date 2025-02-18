package com.busschedule.domain.usecase.schedule

import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class ReadDaysSchedulesUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(dayOfWeek: String) {}
//    operator fun invoke(dayOfWeek: String) = scheduleRepository.raedDaySchedules(dayOfWeek)
}