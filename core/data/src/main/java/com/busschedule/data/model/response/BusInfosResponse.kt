package com.busschedule.data.model.response

import com.busschedule.model.exception.BusStopInfo
import kotlinx.serialization.Serializable

@Serializable
data class BusInfosResponse(
    val busInfoResponses: List<BusStopInfo>
)
