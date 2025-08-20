package com.busschedule.data.remote.model.response

import com.busschedule.model.SubwayStationLineInfo
import kotlinx.serialization.Serializable

@Serializable
data class SubwayStationLineInfoResponse(
    val stationName: String = "",
    val coordinate: Coordinate = Coordinate(),
    val lines: List<String> = emptyList()
)


fun SubwayStationLineInfoResponse.asDomain() = SubwayStationLineInfo(
    stationName = stationName,
    lines = lines
)