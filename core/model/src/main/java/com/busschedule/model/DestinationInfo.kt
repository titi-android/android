package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class DestinationInfo(
    val regionName: String = "",
    val busStopName: String = "",
    val nodeId: String = ""
)