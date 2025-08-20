package com.busschedule.data.data.repository

import com.busschedule.data.remote.api.SubwayApi
import com.busschedule.data.remote.model.response.asDomain
import com.busschedule.domain.repository.SubwayRepository
import com.busschedule.model.SubwayStation
import com.busschedule.model.SubwayStationLineInfo
import javax.inject.Inject

class SubwayRepositoryImpl @Inject constructor(
    private val subwayApi: SubwayApi,
) : SubwayRepository {
    override suspend fun getSubwayStationLineInfo(stName: String): List<SubwayStationLineInfo> =
        subwayApi.getSubwayStationLineInfo(stName).getOrThrow().data!!.map { it.asDomain() }

    override suspend fun getSubwayStation(lineName: String): List<SubwayStation> =
        subwayApi.getSubwayStation(lineName).getOrThrow().data!!.map { it.asDomain() }
}