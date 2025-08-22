package com.busschedule.model

import com.busschedule.model.constant.TransitConst
import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: Int = 0,
    val name: String = "",
    val daysList: List<String> = emptyList(),
    val startTime: List<Int> = emptyList(),
    val endTime: List<Int> = emptyList(),
    val sections: List<TransitInfo> = emptyList(),
    val destinationType: String = TransitConst.BUS.name,
    val destinationName: String = "",
    val isAlarmOn: Boolean
)

@Serializable
data class BusStopInfo(
    val busStopName: String = "",
    val busArrivals: List<BusArrival> = emptyList(),
)

@Serializable
data class BusArrival(
    val arrprevstationcnt: Int = 0,
    val arrtime: Int = 0,
    val nodeid: String = "",
    val nodenm: String = "",
    val routeid: String = "",
    val routeno: String = "",
    val routetp: String = "",
    val vehicletp: String = ""
)