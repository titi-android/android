package com.busschedule.domain.usecase.schedule

import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class PutScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
//    operator fun invoke(scheduleId: Int, schedule: ScheduleRegisterRequest) =
//        scheduleRepository.putSchedule(scheduleId = scheduleId, schedule = schedule)
    operator fun invoke(scheduleId: Int, schedule: ScheduleRegisterRequest) {}
}
