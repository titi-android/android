package com.busschedule.domain.usecase.busstop

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.BusStopRepository
import javax.inject.Inject

class ReadAllBusStopUseCase @Inject constructor(private val busStopRepository: BusStopRepository) {
    suspend operator fun invoke(cityName: String, nodeId: String) = runCatchingIgnoreCancelled {
        busStopRepository.readAllBusStop(cityName, nodeId)
    }
}