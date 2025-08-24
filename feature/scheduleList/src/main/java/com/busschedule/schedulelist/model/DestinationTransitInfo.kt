package com.busschedule.schedulelist.model

import com.busschedule.util.ext.toFormatKrTime

data class DestinationTransitInfo(
    val arrtime: Int = 0,
    val routeno: String,
    val routeType: String,
    val arrprevstationcnt: String,
) {
    fun getArriveBusOrStationNum(): String = routeno
    fun getArriveBusType(): String = routeType
    fun getArriveTime(): String = arrtime.toFormatKrTime()
    fun getRemainingStopsCount(): String = arrprevstationcnt
}
