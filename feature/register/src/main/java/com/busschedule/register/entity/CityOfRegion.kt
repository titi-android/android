package com.busschedule.register.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.register.constant.City
import com.busschedule.register.constant.Region

class CityOfRegion(
    regions: List<Region>
) {
    val regionUiStates = regions.map { RegionUiState(it) }
    private val selectedRegionUiState = regionUiStates.find { it.isSelected }
    val citiesUiState = if (selectedRegionUiState != null) {
        selectedRegionUiState.region.cities.map { CityUiState(it) }
    } else {
        Region.METROPOLITAN.cities.map { CityUiState(it) }
    }
    val selectedCityUiState = citiesUiState.find { it.isSelected }

    fun selectRegion(selectRegion: Region) {
        regionUiStates.forEach { it.isSelected = (it.region == selectRegion) }
    }

    fun selectCity(selectCity: City) {
        citiesUiState.forEach { it.isSelected = (it.city == selectCity) }
    }
}

data class RegionUiState(
    val region: Region,
    private val init: Boolean = false,
) {
    var isSelected by mutableStateOf(init)

    override fun toString(): String =
        "CityUiState(city=$region, isSelected=$isSelected)"
}

data class CityUiState(
    val city: City,
    private val init: Boolean = false,
) {
    var isSelected by mutableStateOf(init)
    override fun toString(): String =
        "CityUiState(city=$city, isSelected=$isSelected)"
}