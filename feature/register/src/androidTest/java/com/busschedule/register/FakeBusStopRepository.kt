package com.busschedule.register

import com.busschedule.domain.repository.BusStopRepository
import com.busschedule.model.exception.BusStopInfo

class FakeBusStopRepository: BusStopRepository {
    override suspend fun readAllBusStop(cityName: String, nodeId: String): List<BusStopInfo> {
        return listOf(BusStopInfo(name = "모란고개", nodeId = "GGB204000087", tmX =37.4370333, tmY = 127.1285333))
    }
}