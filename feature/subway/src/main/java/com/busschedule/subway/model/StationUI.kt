package com.busschedule.subway.model

import com.busschedule.model.Station

data class StationUI(
    val id: Int,
    val stationNm: String,
    val lineNum: String,
)

fun Station.asStateUI(id: Int) = StationUI(
    id = id,
    stationNm = stationNm,
    lineNum = lineNum,
)