package com.busschedule.register.entity

import com.busschedule.domain.model.response.busstop.BusStopInfoResponse

data class SelectBusUiState(
    val input: String = "",
    val busStop: List<BusStopInfoResponse> = emptyList(),
) {
    fun findBusStop(name: String, lat: Double, lng: Double) =
        requireNotNull(busStop.filter { it.name == name }.find { it.tmX == lat && it.tmY == lng }) {
            "BusStop not found"
    }
}
