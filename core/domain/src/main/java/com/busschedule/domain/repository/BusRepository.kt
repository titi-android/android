package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import kotlinx.coroutines.flow.Flow

interface BusRepository {
    fun readAllBus(cityName: String,busStopId: String): Flow<ApiState<List<String>>>
}