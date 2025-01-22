package com.busschedule.register.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.register.constant.City
import com.busschedule.register.constant.Region

class CityOfRegion(
    regions: List<Region>,
) {
    val regionUiStates = regions.map { RegionUiState(it) }

    var citiesUiState: List<CityUiState> by mutableStateOf(Region.METROPOLITAN.cities.map { CityUiState(it) })

    val selectedCityUiState: CityUiState?
        get() = citiesUiState.find { it.isSelected }

    fun selectRegion(selectRegion: Region) {
        regionUiStates.forEach { it.isSelected = (it.region == selectRegion) }
        val region = getSelectedRegion() ?: Region.METROPOLITAN
        updateCitiesUiState(region)
    }

    fun selectCity(selectCity: City) {
        citiesUiState.forEach { it.isSelected = (it.city == selectCity) }
    }

    private fun getSelectedRegion() = regionUiStates.find { it.isSelected }?.region

    private fun updateCitiesUiState(region: Region) {
        citiesUiState = region.cities.map { CityUiState(it) }
    }

    fun getSelectedCityName() = selectedCityUiState?.city?.value ?: ""
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