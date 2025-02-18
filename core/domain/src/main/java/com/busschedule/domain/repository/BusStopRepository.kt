package com.busschedule.domain.repository

import com.busschedule.model.exception.BusStopInfo

interface BusStopRepository {
    suspend fun readAllBusStop(cityName: String, nodeId: String): List<BusStopInfo>
}