package com.busschedule.domain.repository

import com.busschedule.model.BusInfo

interface BusRepository {
    suspend fun readAllBus(cityName: String,busStopId: String): List<BusInfo>
}