package com.busschedule.domain.usecase.schedule

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class DeleteScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(scheduleId: Int) = runCatchingIgnoreCancelled {
        scheduleRepository.deleteSchedule(scheduleId)
    }
}