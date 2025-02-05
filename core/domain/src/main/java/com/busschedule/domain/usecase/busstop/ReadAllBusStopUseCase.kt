package com.busschedule.domain.usecase.busstop

import com.busschedule.domain.repository.BusStopRepository
import javax.inject.Inject

class ReadAllBusStopUseCase @Inject constructor(private val busStopRepository: BusStopRepository){
    operator fun invoke(cityName: String, busStop: String) = busStopRepository.readAllBusStop(cityName, busStop)
}