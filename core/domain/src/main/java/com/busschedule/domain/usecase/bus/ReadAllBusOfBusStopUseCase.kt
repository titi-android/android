package com.busschedule.domain.usecase.bus

import com.busschedule.domain.repository.BusRepository
import javax.inject.Inject

class ReadAllBusOfBusStopUseCase @Inject constructor(private val busRepository: BusRepository) {
//    operator fun invoke(cityName: String, busStopId: String) = busRepository.readAllBus(cityName, busStopId)
    operator fun invoke(cityName: String, busStopId: String) {}
}