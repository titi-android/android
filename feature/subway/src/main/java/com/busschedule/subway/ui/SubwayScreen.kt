package com.busschedule.subway.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.subway.SubwayViewModel
import com.busschedule.subway.component.SelectStationBox
import com.busschedule.subway.component.SelectStationPrefixText
import com.busschedule.subway.component.SubwayLineCard
import com.busschedule.subway.component.SubwayStationComponent
import com.busschedule.subway.model.StationLineUI
import com.busschedule.subway.model.SubwayStationUI
import com.busschedule.util.ext.customNavigationBarPadding
import com.busschedule.util.ext.noRippleClickable
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainButton
import core.designsystem.component.textfield.SearchTextField
import core.designsystem.shadow.titiShadow
import core.designsystem.theme.Background
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mFooter

@Composable
fun SubwayScreen(viewModel: SubwayViewModel = hiltViewModel()) {

    val subwayStationsState by viewModel.subwayStations.collectAsStateWithLifecycle()
    val stationLines by viewModel.subwayManager.stationLines.collectAsStateWithLifecycle()

    var selectStations by remember { mutableStateOf<Pair<SubwayStationUI?, SubwayStationUI?>>(null to null) }
    val stationDirection by remember {
        derivedStateOf {
            if (selectStations.first?.id == null || selectStations.second == null) StationDirection.NONE
            else if (selectStations.first!!.id > selectStations.second!!.id) StationDirection.UP
            else StationDirection.DOWN
        }
    }

    val onClickStation: (id: Int) -> Unit = { id ->
        selectStations =
            if (selectStations.first != null && selectStations.second != null) selectStations.copy(
                first = null,
                second = null
            )
            else if (selectStations.first == null) selectStations.copy(first = subwayStationsState[id])
            else selectStations.copy(second = subwayStationsState[id])
    }

    val checkSelectStation: (id: Int) -> SelectStation = {
        if (selectStations.first == null || selectStations.second == null) SelectStation.NOT
        else if (it == selectStations.first!!.id) SelectStation.START
        else if (it == selectStations.second!!.id) SelectStation.END
        else if (it in (selectStations.first!!.id..selectStations.second!!.id)) SelectStation.RANGE
        else if (selectStations.first!!.id > selectStations.second!!.id) {
            if (it in (selectStations.second!!.id..selectStations.first!!.id)) SelectStation.RANGE
            else SelectStation.NOT
        } else SelectStation.NOT
    }

    val isExistSelectStation by remember {
        derivedStateOf { !(selectStations.first == null || selectStations.second == null) }
    }

    val isShowSelectStationBox by remember {
        derivedStateOf { !(selectStations.first == null && selectStations.second == null) }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .customNavigationBarPadding(true)
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 10.dp)
    ) {
        SubwayAppBar(
            stationLines = stationLines,
            onSubwayStationLineClicked = { viewModel.fetchGetSubwayStation(it) },
            onSearch = { viewModel.fetchGetSubwayStationLineInfo(it) })
        HeightSpacer(8.dp)
        Box(Modifier.weight(1f)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = subwayStationsState, key = { it.id }) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.noRippleClickable { onClickStation(it.id) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val stationState = checkSelectStation(it.id)
                            SubwayStationComponent(
                                isSelected = stationState,
                                stationDirection = stationDirection,
                                selectedColor = Color(0xFFFF0000)
                            )
                            WidthSpacer(18.dp)

                            when (stationState) {
                                SelectStation.START -> {
                                    SelectStationPrefixText(name = "출발", color = Color(0xFF2E4291))
                                    WidthSpacer(8.dp)
                                }

                                SelectStation.END -> {
                                    SelectStationPrefixText(name = "도착", color = Color(0xFF2E4291))
                                    WidthSpacer(8.dp)
                                }

                                else -> {}
                            }
                            Text(it.stationNm, style = mFooter)
                        }
                    }
                }
            }
            if (isShowSelectStationBox) {
                SelectStationBox()
            }
        }

        MainButton(text = "스케줄 등록", enabled = isExistSelectStation) { }
    }
}

@Composable
fun ColumnScope.SubwayAppBar(
    stationLines: List<StationLineUI>,
    onSubwayStationLineClicked: (stName: String) -> Unit,
    onSearch: (search: String) -> Unit,
) {
    var input by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = TextWColor,
                contentColor = Primary
            ),
            modifier = Modifier.titiShadow(
                color = Color.Black.copy(alpha = 0.1f),
                blurRadius = 4.dp,
                borderRadius = 100.dp
            ),
            onClick = { }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "ic_back_arrow",
                modifier = Modifier.size(16.dp)
            )
        }

        SearchTextField(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            value = input,
            onValueChange = { input = it },
            placeholder = "지하철역 이름을 검색해보세요"
        ) {
            onSearch(input)
        }
    }
    LazyRow(
        modifier = Modifier,
//                contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = stationLines, key = { it.name }) {
            SubwayLineCard(name = it.name, isSelected = it.isSelected) {
                onSubwayStationLineClicked(it.name)
            }
        }
    }
}

enum class SelectStation {
    START,
    END,
    RANGE,
    NOT
}

enum class StationDirection {
    UP,
    DOWN,
    NONE
}