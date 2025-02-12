package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.response.busstop.BusInfo
import kotlinx.coroutines.flow.Flow

interface BusRepository {
    fun readAllBus(cityName: String,busStopId: String): Flow<ApiState<List<BusInfo>>>
}