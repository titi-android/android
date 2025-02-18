package com.busschedule.data.repository

import com.busschedule.data.api.BusStopApi
import com.busschedule.domain.repository.BusStopRepository
import javax.inject.Inject

class BusStopRepositoryImpl @Inject constructor(private val busStopApi: BusStopApi): BusStopRepository {

}