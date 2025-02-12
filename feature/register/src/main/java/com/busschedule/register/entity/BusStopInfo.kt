package com.busschedule.register.entity

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import com.busschedule.domain.model.response.busstop.BusResponse
import com.busschedule.util.entity.BusType


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

@Stable
data class SelectedBusUI (val busStop: String = "", val nodeId: String = "", val buses: List<Bus> = emptyList())

data class Bus(
    val name: String,
    val type: BusType = BusType.지정,
    val selectedInit: Boolean = false
) {
    var isSelected by mutableStateOf(selectedInit)
}