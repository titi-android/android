package com.busschedule.register.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.busschedule.register.constant.City
import com.busschedule.register.constant.Region

class CityOfRegion(
    private val regions: List<Region> = Region.entries,
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

    fun selectCity(selectCity: String) = apply{
        this.citiesUiState.forEach { it.isSelected = (it.city.value == selectCity) }
    }

    private fun getSelectedRegion() = regionUiStates.find { it.isSelected }?.region

    private fun updateCitiesUiState(region: Region) {
        citiesUiState = region.cities.map { CityUiState(it) }
    }
    private fun isExistCity(input: String): Pair<Region, City>? {
        regions.forEach { region ->
            val result = region.cities.find { city -> city.value == input }
            if (result != null) {
                return region to result
            }
        }
        return null
    }
    fun searchCity(input: String): Boolean {
        val result = isExistCity(input)
        if (result != null) {
            selectRegion(result.first)
            selectCity(result.second)
            return true
        }
        return false
    }



    fun getSelectedCityName() = selectedCityUiState?.city?.value ?: "도시(지역)"
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