package com.busschedule.data.repository

import com.busschedule.data.remote.api.BusStopApi
import com.busschedule.domain.repository.BusStopRepository
import com.busschedule.model.exception.BusStopInfo
import javax.inject.Inject

class BusStopRepositoryImpl @Inject constructor(private val busStopApi: BusStopApi) :
    BusStopRepository {
    override suspend fun readAllBusStop(
        cityName: String,
        nodeId: String,
    ): List<BusStopInfo> =
        busStopApi.readAllBusStop(cityName, nodeId).getOrThrow().data?.busInfosResponse
            ?: emptyList()


}