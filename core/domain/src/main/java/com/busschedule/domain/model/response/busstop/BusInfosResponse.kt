package com.busschedule.domain.model.response.busstop

import kotlinx.serialization.Serializable

@Serializable
data class BusInfosResponse(
    val busInfosResponse: List<BusStopInfoResponse>
)


@Serializable
data class BusStopInfoResponse(
    val name: String,
    val nodeId: String,
    val tmX: Double,
    val tmY: Double
)