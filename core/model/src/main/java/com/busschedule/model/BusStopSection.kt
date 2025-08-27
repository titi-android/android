package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class BusStopSection(
    val regionName: String = "",
    val busStopName: String = "",
    val nodeId: String = "",
    val busList: List<BusInfo> = emptyList()
)