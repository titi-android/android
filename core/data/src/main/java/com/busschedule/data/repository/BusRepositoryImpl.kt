package com.busschedule.data.repository

import com.busschedule.data.api.BusApi
import com.busschedule.domain.repository.BusRepository
import com.busschedule.model.BusInfo
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(private val busApi: BusApi): BusRepository {
    override suspend fun readAllBus(cityName: String, busStopId: String): List<BusInfo> =
        busApi.readAllBusOfBusStop(cityName, busStopId).getOrThrow()

}