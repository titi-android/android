package com.busschedule.register.entity

data class SelectRegionUiState (
    val input: String = "",
    val region: CityOfRegion = CityOfRegion()
)