package com.busschedule.data.repository

import com.busschedule.data.network.BusStopApi
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.response.busstop.BusInfosResponse
import com.busschedule.domain.model.safeFlow
import com.busschedule.domain.repository.BusStopRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusStopRepositoryImpl @Inject constructor(private val busStopApi: BusStopApi): BusStopRepository {
    override fun readAllBusStop(cityName: String, nodeId: String): Flow<ApiState<BusInfosResponse>> = safeFlow {
        busStopApi.readAllBusStop(cityName, nodeId)
    }
}