package com.busschedule.domain.usecase.schedule

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.ScheduleRepository
import com.busschedule.model.BusInfo
import javax.inject.Inject

class PutScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(
        scheduleId: Int,
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
        scheduleRepository.putSchedule(
            scheduleId = scheduleId,
            name = name,
            daysList = daysList,
            startTime = startTime,
            endTime = endTime,
            regionName = regionName,
            busStopName = busStopName,
            nodeId = nodeId,
            busInfos = busInfos,
            isAlarmOn = isAlarmOn,
        )
    }
}
