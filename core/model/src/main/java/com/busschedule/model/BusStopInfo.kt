package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class BusStopInfo(
    val region: String = "",
    val busStop: String = "",
    val nodeId: String = "",
//    val buses: List<BusInfo> = emptyList(),
)
