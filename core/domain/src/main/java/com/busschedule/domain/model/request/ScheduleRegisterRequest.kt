package com.busschedule.domain.model.request

import com.busschedule.domain.model.response.busstop.BusInfo
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleRegisterRequest(
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val regionName: String = "",
    val busStopName: String = "",
    val nodeId: String = "",
    val busInfos: List<BusInfo> = emptyList(),
    val isAlarmOn: Boolean
)