package com.busschedule.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.constant.City
import com.busschedule.register.constant.Region
import com.busschedule.register.entity.CityUiState
import com.busschedule.register.entity.RegionUiState
import com.example.connex.ui.domain.ApplicationState

@Composable
fun SelectRegionScreen(
    appState: ApplicationState, registerBusScheduleViewModel: RegisterBusScheduleViewModel = hiltViewModel()
) {

    val selectedRegionUiState by registerBusScheduleViewModel.cityOfRegion.collectAsStateWithLifecycle()

    val btnEnable by remember {
        derivedStateOf { selectedRegionUiState.selectedCityUiState != null }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", modifier = Modifier.clickable { appState.popBackStack() })
        }
        Row(modifier = Modifier.weight(1f)) {
            RegionArea(selectedRegionUiState.regionUiStates) { selectedRegionUiState.selectRegion(it) }
            CityArea(selectedRegionUiState.citiesUiState) { selectedRegionUiState.selectCity(it) }
        }
        Button(onClick = { appState.popBackStack() }, enabled = btnEnable) {
            Text(text = "완료")
        }
    }
}

@Composable
fun RowScope.RegionArea(regions: List<RegionUiState>, select: (Region) -> Unit) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = Modifier.weight(0.4f)) {
        items(items = regions, key = { it.region }) {
            Text(
                text = it.region.value,
                modifier = Modifier
                    .background(color = if (it.isSelected) Color.Red else Color.Gray)
                    .clickable { select(it.region) })
        }
    }
}

@Composable
fun RowScope.CityArea(cities: List<CityUiState>, select: (City) -> Unit) {

    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = Modifier.weight(0.6f)) {
        items(items = cities, key = { it.city }) {
            Text(
                text = it.city.value,
                modifier = Modifier
                    .background(color = if (it.isSelected) Color.Red else Color.LightGray)
                    .clickable { select(it.city) })
        }
    }
}

