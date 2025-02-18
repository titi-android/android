package com.busschedule.domain.usecase.schedule

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.BusInfo
import javax.inject.Inject

class PostScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(
        name: String = "",
        daysList: List<String> = emptyList(),
        startTime: String = "",
        endTime: String = "",
        regionName: String = "",
        busStopName: String = "",
        nodeId: String = "",
        busInfos: List<BusInfo> = emptyList(),
        isAlarmOn: Boolean,
    ) = runCatchingIgnoreCancelled {
        scheduleRepository.postSchedule(
            name,
            daysList,
            startTime,
            endTime,
            regionName,
            busStopName,
            nodeId,
            busInfos,
            isAlarmOn,
        )
    }
}