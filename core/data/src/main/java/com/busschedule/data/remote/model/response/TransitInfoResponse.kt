package com.busschedule.data.remote.model.response

import com.busschedule.model.BusStopInfo
import com.busschedule.model.Subway
import com.busschedule.model.TransitInfo
import kotlinx.serialization.Serializable

@Serializable
data class TransitInfoResponse(
    val type: String,
    val busStop: BusStopInfo?,
    val subway: Subway?,
    val orderIndex: Int
)

fun TransitInfoResponse.asDomain() = TransitInfo(
    type = type,
    busStop = busStop,
    subway = subway,
    orderIndex = orderIndex
)