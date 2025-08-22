package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteInfo(
    val type: String = "BUS", // BUS, SUBWAY
    val busStopSection: BusStopSection?,
    val subwaySection: SubwaySection?,
)
