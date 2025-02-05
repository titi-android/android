package com.busschedule.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.busschedule.register.component.SearchTextField
import com.busschedule.register.constant.City
import com.busschedule.register.constant.Region
import com.busschedule.register.entity.CityUiState
import com.busschedule.register.entity.RegionUiState
import com.busschedule.register.entity.SelectRegionUiState
import com.busschedule.register.entity.TextBoxColor
import com.example.connex.ui.domain.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.MainButton
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.theme.BackgroundColor

@Composable
fun SelectRegionScreen(
    appState: ApplicationState,
    registerBusScheduleViewModel: RegisterBusScheduleViewModel = hiltViewModel(),
) {

    val selectedRegionUiState by registerBusScheduleViewModel.selectRegionUiState.collectAsStateWithLifecycle(
        SelectRegionUiState()
    )

    val btnEnable by remember {
        derivedStateOf { selectedRegionUiState.region.selectedCityUiState != null }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(title = "도시 이름 검색") {}
        SearchTextField(
            value = selectedRegionUiState.input,
            onValueChange = { registerBusScheduleViewModel.updateRegionInput(it) },
            placeholder = "도시(지역) 이름 검색") {
            if (selectedRegionUiState.region.searchCity(it).not()) {
                appState.showToastMsg("일치하는 지역이 없습니다.")
            }
        }
        HeightSpacer(height = 16.dp)
        Row(modifier = Modifier.weight(1f)) {
            RegionArea(selectedRegionUiState.region.regionUiStates) {
                selectedRegionUiState.region.selectRegion(it)
            }
            CityArea(selectedRegionUiState.region.citiesUiState,) { selectedRegionUiState.region.selectCity(it) }
        }
        HeightSpacer(height = 16.dp)
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            MainButton(modifier = Modifier.padding(horizontal = 16.dp), text = "완료", enabled = btnEnable) {
                appState.popBackStack()
            }
        }
    }
}

@Composable
fun RowScope.RegionArea(regions: List<RegionUiState>, select: (Region) -> Unit) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = Modifier.weight(1f)) {
        items(items = regions, key = { it.region }) {
            RegionTextBox(
                region = it.region.value,
                unSelectedColor = Color(0xFFE9E9E9),
                isSelected = it.isSelected
            ) { select(it.region) }
        }
    }
}

@Composable
fun RegionTextBox(region: String, unSelectedColor: Color, isSelected: Boolean, select: () -> Unit) {
    val textColor = if (isSelected) {
        TextBoxColor(container = Color(0xFF2E2E34), content = Color.White)
    } else {
        TextBoxColor(container = unSelectedColor, content = Color(0xFF232527))
    }
    Text(
        text = region,
        color = textColor.content,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = textColor.container)
            .padding(start = 16.dp, top = 10.5.dp, bottom = 10.5.dp)
            .clickable { select() })
}

@Composable
fun RowScope.CityArea(cities: List<CityUiState>, select: (City) -> Unit) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = Modifier.weight(1f)) {
        items(items = cities, key = { it.city }) {
            RegionTextBox(
                region = it.city.value,
                unSelectedColor = Color.White,
                isSelected = it.isSelected
            ) { select(it.city) }
        }
    }
}