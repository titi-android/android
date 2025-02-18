package com.busschedule.model.exception

import kotlinx.serialization.Serializable

@Serializable
data class BusStopInfo(
    val name: String,
    val nodeId: String,
    val tmX: Double,
    val tmY: Double
)
