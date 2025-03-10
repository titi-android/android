package com.busschedule.data.remote.model.response

import com.busschedule.model.exception.BusStopInfo
import kotlinx.serialization.Serializable

@Serializable
data class BusInfosResponse(
    val busInfosResponse: List<BusStopInfo>
)
