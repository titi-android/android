package com.busschedule.register.model

import com.busschedule.model.RecentlySearchBusStop

data class SelectRegionUiState (
    val input: String = "",
    val region: CityOfRegion = CityOfRegion()
)

data class SelectBusStopUiState (
    val input: String = "",
    val recentlySearchBusStop: List<RecentlySearchBusStop> = emptyList()
)

data class AddBusDialogUiState (
    val input: String = "",
    val bus: List<Bus> = emptyList(),
)


