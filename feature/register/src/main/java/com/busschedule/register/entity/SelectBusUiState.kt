package com.busschedule.register.entity

data class SelectBusUiState(
    val input: String = "",
    val busList: List<Bus> = emptyList(),
)
