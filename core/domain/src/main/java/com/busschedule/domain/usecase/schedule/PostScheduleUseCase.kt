package com.busschedule.domain.usecase.schedule

import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class PostScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
//    operator fun invoke(scheduleRegisterRequest: ScheduleRegisterRequest) = scheduleRepository.postSchedule(scheduleRegisterRequest)
    operator fun invoke(scheduleRegisterRequest: ScheduleRegisterRequest) {}
}