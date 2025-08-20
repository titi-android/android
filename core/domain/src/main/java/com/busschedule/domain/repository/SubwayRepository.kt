package com.busschedule.domain.repository

import com.busschedule.model.SubwayStationLineInfo

interface SubwayRepository {

    suspend fun getSubwayStationLineInfo(stName: String): List<SubwayStationLineInfo>
}