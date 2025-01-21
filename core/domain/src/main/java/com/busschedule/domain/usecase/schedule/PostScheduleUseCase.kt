package com.busschedule.domain.usecase.schedule

import com.busschedule.domain.model.request.ScheduleRegister
import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class PostScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(scheduleRegister: ScheduleRegister) = scheduleRepository.postSchedule(scheduleRegister)
}