package com.busschedule.domain.usecase.schedule

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class ReadDaysSchedulesUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(dayOfWeek: String)  = runCatchingIgnoreCancelled {
        scheduleRepository.readDaySchedules(dayOfWeek)
    }
}