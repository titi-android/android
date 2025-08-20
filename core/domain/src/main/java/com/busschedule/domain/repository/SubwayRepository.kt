package com.busschedule.domain.repository

import com.busschedule.model.SubwayStation
import com.busschedule.model.SubwayStationLineInfo

interface SubwayRepository {

    suspend fun getSubwayStationLineInfo(stName: String): List<SubwayStationLineInfo>

    suspend fun getSubwayStation(lineName: String): List<SubwayStation>
}