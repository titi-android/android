package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.response.busstop.BusInfosResponse
import kotlinx.coroutines.flow.Flow

interface BusStopRepository {
    fun readAllBusStop(cityName: String, busStop: String): Flow<ApiState<BusInfosResponse>>
}