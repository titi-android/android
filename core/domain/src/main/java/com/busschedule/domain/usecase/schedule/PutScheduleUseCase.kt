package com.busschedule.domain.usecase.schedule

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import javax.inject.Inject

class PutScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(
        scheduleId: Int,
        name: String = "",
        daysList: List<String> = emptyList(),
        startTime: String = "",
        endTime: String = "",
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean,
    ) = runCatchingIgnoreCancelled {
        scheduleRepository.putSchedule(
            scheduleId = scheduleId,
            name = name,
            daysList = daysList,
            startTime = startTime,
            endTime = endTime,
            routeInfos = routeInfos,
            destinationInfo = destinationInfo,
            isAlarmOn = isAlarmOn,
        )
    }
}
