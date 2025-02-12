package com.busschedule.register.entity

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.toMutableStateList
import com.busschedule.domain.model.response.busstop.BusResponse


@Stable
data class BusStopInfo (val busStop: String = "", val nodeId: String = "", val busesInit: List<BusResponse> = emptyList()) {
    private val buses = busesInit.toMutableStateList()

    fun remove(name: String) {
        Log.d("daeYoung", "buses: $buses")
        buses.removeIf { it.name == name }
        Log.d("daeYoung", "buses: $buses")
    }
    fun getBuses() = buses.toList()
}

@JvmInline
value class Bus(val name: String)