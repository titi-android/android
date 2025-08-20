package com.busschedule.data.remote.model.response

import com.busschedule.model.SubwayStation
import kotlinx.serialization.Serializable

@Serializable
data class SubwayStationResponse(
    val stationCd: String,
    val stationNm: String,
    val stationNmEng: String,
    val lineNum: String,
    val frCode: String,
)

fun SubwayStationResponse.asDomain() =
    SubwayStation(
        stationCd = stationCd,
        stationNm = stationNm,
        lineNum = lineNum,
    )