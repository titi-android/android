package com.busschedule.register.entity

data class SelectRegionUiState (
    val input: String = "",
    val region: CityOfRegion = CityOfRegion()
)

data class AddBusDialogUiState (
    val input: String = "",
    val bus: List<Bus> = emptyList()
)