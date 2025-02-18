package com.busschedule.domain.usecase.bus

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.BusRepository
import javax.inject.Inject

class ReadAllBusOfBusStopUseCase @Inject constructor(private val busRepository: BusRepository) {
    suspend operator fun invoke(cityName: String, busStopId: String) = runCatchingIgnoreCancelled {
        busRepository.readAllBus(cityName, busStopId)
    }
}