package com.busschedule.register.entity

data class SelectBusUiState(
    val input: String = "",
    val busStop: List<BusStopInfo> = emptyList(),
)
