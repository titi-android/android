package com.busschedule.domain.usecase.bus

import com.busschedule.domain.repository.BusRepository
import javax.inject.Inject

class ReadAllBusUseCase @Inject constructor(private val busRepository: BusRepository) {
    operator fun invoke(cityName: String, busStopId: String) = busRepository.readAllBus(cityName, busStopId)
}