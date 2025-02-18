package com.busschedule.data.repository

import com.busschedule.data.api.BusApi
import com.busschedule.domain.repository.BusRepository
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(private val busApi: BusApi): BusRepository {
//    override fun readAllBus(cityName: String, busStopId: String): Flow<ApiResult<List<BusInfo>>> =
//        safeFlowNotJson { busApi.readAllBusOfBusStop(cityName, busStopId) }
}