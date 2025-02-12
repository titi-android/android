package com.busschedule.data.repository

import com.busschedule.data.network.BusApi
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.response.busstop.BusInfo
import com.busschedule.domain.model.safeFlowNotJson
import com.busschedule.domain.repository.BusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(private val busApi: BusApi): BusRepository {
    override fun readAllBus(cityName: String, busStopId: String): Flow<ApiState<List<BusInfo>>> =
        safeFlowNotJson { busApi.readAllBusOfBusStop(cityName, busStopId) }
}