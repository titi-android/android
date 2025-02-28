package com.busschedule.domain.usecase.schedule

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import javax.inject.Inject

class PostScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(
        name: String = "",
        daysList: List<String> = emptyList(),
        startTime: String = "",
        endTime: String = "",
        routeInfos: List<RouteInfo>,
        destinationInfo: DestinationInfo,
        isAlarmOn: Boolean,
    ) = runCatchingIgnoreCancelled {
        scheduleRepository.postSchedule(
            name,
            daysList,
            startTime,
            endTime,
            routeInfos,
            destinationInfo,
            isAlarmOn,
        )
    }
}