package com.busschedule.domain.usecase.schedule

import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class ReadScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(scheduleId: Int) {}
//    operator fun invoke(scheduleId: Int) = scheduleRepository.readSchedule(scheduleId)
}