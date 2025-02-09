package com.busschedule.register.entity


data class BusStopInfo (val busStop: String, val buses: List<Bus>)

@JvmInline
value class Bus(val name: String)

//fun BusStopInfoResponse.asDomain() = BusStopInfo(
//    busStop = this,
//    buses = emptyList()
//)