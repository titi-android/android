@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.busschedule.widget.widget

import com.busschedule.model.ScheduleTicket
import kotlinx.serialization.Serializable

@Serializable
sealed interface ScheduleInfo {
    @Serializable
    data object Loading : ScheduleInfo

    @Serializable
    data class Available(
        val scheduleName: String,
        val busStop: String,
        val busArrivalInfo: List<BusArrivalData>,
    ) : ScheduleInfo

    @Serializable
    sealed interface Unavailable : ScheduleInfo {
        @Serializable
        data object JWT401 : Unavailable
        @Serializable
        data object UnExpected : Unavailable
        @Serializable
        data object DataIsNull : Unavailable
    }
//
//    @Serializable
//    data object Unavailable : ScheduleInfo
}

@Serializable
data class BusArrivalData(
    val bus: String,
    val type: String,
    val arrivalTime: Int,
)

fun ScheduleTicket.toWidgetState() = ScheduleInfo.Available(
    scheduleName = this.name,
    busStop = this.busStopInfos[0].busStopName,
    busArrivalInfo = this.busStopInfos[0].busInfos.map {
        BusArrivalData(
            bus = it.routeno,
            type = it.routetp,
            arrivalTime = it.arrtime
        )
    }
)