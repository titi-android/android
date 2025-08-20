package com.busschedule.subway.model

import com.busschedule.model.SubwayStation

data class SubwayStationUI(
    val id: Int,
    val stationNm: String,
    val lineNum: String,
)


fun SubwayStation.asState(id: Int) = SubwayStationUI(
    id = id,
    stationNm = stationNm,
    lineNum = lineNum,
)