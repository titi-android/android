package com.busschedule.domain.usecase.schedule

import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class ReadTodaySchedulesUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke() = scheduleRepository.raedTodaySchedules()
}