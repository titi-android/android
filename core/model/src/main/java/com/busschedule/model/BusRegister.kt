package com.busschedule.model

import kotlinx.serialization.Serializable



@Serializable
data class BusRegister(
    val regionName: String = "",
    val busStopName: String = "",
    val nodeId: String = "",
    val busInfos: List<BusInfo> = emptyList()
)