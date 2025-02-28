package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class BusStop(
    val id: Int = 0,
    val region: String = "",
    val busStop: String = "",
    val nodeId: String = "",
) {
    fun isEmpty() = busStop.isNotBlank() && nodeId.isNotBlank()
}


fun BusStop.asDestinationInfo() = DestinationInfo(
    regionName = region,
    busStopName = busStop,
    nodeId = nodeId,
)

fun DestinationInfo.asBusStop() = BusStop(
    region = regionName,
    busStop = busStopName,
    nodeId = nodeId,
)