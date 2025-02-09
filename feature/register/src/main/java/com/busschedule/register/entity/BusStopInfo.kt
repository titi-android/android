package com.busschedule.register.entity

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.toMutableStateList


@Stable
data class BusStopInfo (val busStop: String, val busesInit: List<Bus>) {
    private val buses = busesInit.toMutableStateList()
//    private val buses = mutableStateListOf<Bus>().apply { addAll(busesInit) }

    fun remove(name: String) {
        Log.d("daeYoung", "buses: $buses")
        buses.removeIf { it.name == name }
        Log.d("daeYoung", "buses: $buses")
    }
    fun getBuses() = buses.toList()
}

@JvmInline
value class Bus(val name: String)

//fun BusStopInfoResponse.asDomain() = BusStopInfo(
//    busStop = this,
//    buses = emptyList()
//)