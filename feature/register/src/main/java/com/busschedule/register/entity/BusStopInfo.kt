package com.busschedule.register.entity

import com.busschedule.domain.model.response.busstop.BusStopInfoResponse


data class BusStopInfo (val busStop: BusStopInfoResponse, val buses: List<Bus>)

@JvmInline
value class Bus(val name: String)

fun BusStopInfoResponse.asDomain() = BusStopInfo(
    busStop = this,
    buses = emptyList()
)