package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import kotlinx.coroutines.flow.Flow

interface BusStopRepository {
    fun checkBusStop(cityName: String, busStop: String): Flow<ApiState<String>>
}