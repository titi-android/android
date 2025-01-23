package com.busschedule.domain.usecase.busstop

import com.busschedule.domain.repository.BusStopRepository
import javax.inject.Inject

class CheckBusStopUseCase @Inject constructor(private val busStopRepository: BusStopRepository){
    operator fun invoke(cityName: String, busStop: String) = busStopRepository.checkBusStop(cityName, busStop)
}