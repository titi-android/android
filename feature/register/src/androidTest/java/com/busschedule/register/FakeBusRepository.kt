package com.busschedule.register

import com.busschedule.domain.repository.BusRepository
import com.busschedule.model.BusInfo

class FakeBusRepository: BusRepository {
    override suspend fun readAllBus(cityName: String, busStopId: String): List<BusInfo> {
        TODO("Not yet implemented")
    }
}