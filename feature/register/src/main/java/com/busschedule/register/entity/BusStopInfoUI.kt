package com.busschedule.register.entity

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import com.busschedule.domain.model.response.busstop.BusInfo
import com.busschedule.model.BusStopInfo
import com.busschedule.model.BusType


@Stable
data class BusStopInfoUI(
    val busStop: String = "",
    val nodeId: String = "",
    val busesInit: List<BusInfo> = emptyList(),
) {
    private val buses = busesInit.toMutableStateList()

    fun remove(name: String) {
        Log.d("daeYoung", "buses: $buses")
        buses.removeIf { it.name == name }
        Log.d("daeYoung", "buses: $buses")
    }

    fun getBuses() = buses.toList()
}

fun BusStopInfoUI.asBusStopInfo(region: String) = BusStopInfo(
    region = region,
    busStop = busStop,
    nodeId = nodeId,
//    buses = this.getBuses()
)


@Stable
data class SelectedBusUI(
    val busStop: String = "",
    val nodeId: String = "",
    val buses: List<Bus> = emptyList(),
)

data class Bus(
    val name: String,
    val type: BusType = BusType.지정,
    private val selectedInit: Boolean = false,
) {
    var isSelected by mutableStateOf(selectedInit)
}