package com.busschedule.schedulelist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.schedulelist.ScheduleListViewModel
import com.busschedule.schedulelist.component.ScheduleTicket
import com.busschedule.schedulelist.model.ScheduleListUiState
import com.busschedule.util.entity.BusType
import com.busschedule.util.entity.DayOfWeekUi
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.DayOfWeekCard
import core.designsystem.component.HeightSpacer
import core.designsystem.component.button.MainButton
import core.designsystem.svg.IconPack
import core.designsystem.svg.myiconpack.IcRefresh
import core.designsystem.svg.myiconpack.IcSetting
import core.designsystem.theme.Background
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@Composable
fun ScheduleListScreen(
    appState: ApplicationState,
    scheduleListViewModel: ScheduleListViewModel = hiltViewModel(),
) {

    val uiState by scheduleListViewModel.scheduleListUiState.collectAsStateWithLifecycle(
        ScheduleListUiState()
    )

    LaunchedEffect(Unit) {
        listOf(
            async { scheduleListViewModel.initFCMToken() },
            async { scheduleListViewModel.fetchReadTodaySchedules() }
        ).awaitAll()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 10.dp)
    ) {
        HeightSpacer(height = 6.dp)
        ScheduleListAppBar { appState.navigateToSetting() }
        HeightSpacer(height = 16.dp)
        DayOfWeekSelectArea(dayOfWeekUi = uiState.dayOfWeeks) {
            scheduleListViewModel.fetchReadDayOfWeekSchedules(it)
        }
        Box(modifier = Modifier.weight(1f)) {
            val lazyListState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                state = lazyListState,
                contentPadding = PaddingValues(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = uiState.schedules, key = { it.id }) { schedule ->
                    val color = if (schedule.busInfos.isEmpty()) BusType.지정 else BusType.find(schedule.busInfos[0].routetp)
                    ScheduleTicket(
                        ticketColor = color.color,
                        holeColor = Background,
                        schedule = schedule,
                        ticketT1Color = color.colorT1,
                        ticketT2Color = color.colorT2,
                        changeNotifyState = {
                            schedule.updateAlarm()
                            scheduleListViewModel.fetchPutScheduleAlarm(scheduleId = schedule.id) {
                                schedule.updateAlarm()
                            }
                        },
                        onEdit = { appState.navigateToRegister(schedule.id) }) {
                        scheduleListViewModel.fetchDeleteSchedules(schedule.id)
                    }
                }
            }
            RefreshIcon { scheduleListViewModel.fetchReadDayOfWeekSchedules(uiState.getSelectedDayOfWeek()) }
        }

        MainButton(text = "스케줄 등록") { appState.navigate(com.busschedule.navigation.Route.RegisterGraph.RegisterSchedule()) }
    }
}


@Composable
fun ScheduleListAppBar(onClickSetting: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        /*TODO: 버스링 로고 및 텍스트로 넣을 것 */
        Text(text = "버스링")
        Icon(imageVector = IconPack.IcSetting,
            contentDescription = "ic_setting",
            tint = Primary,
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickSetting() })
    }
}


@Composable
fun DayOfWeekSelectArea(dayOfWeekUi: List<DayOfWeekUi>, requestDaySchedule: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        dayOfWeekUi.forEach { day ->
            DayOfWeekCard(
                text = day.dayOfWeek.value,
                isSelected = day.isSelected,
                isShadow = true
            ) {
                dayOfWeekUi.forEach { cur ->
                    if (day.dayOfWeek == cur.dayOfWeek) cur.updateSelected(true)
                    else cur.updateSelected(false)
                }
                requestDaySchedule(day.getDayOfWeeks())
            }
        }
    }
}

@Composable
fun BoxScope.RefreshIcon(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 22.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Primary,
            contentColor = TextWColor
        ), onClick = { onClick() }) {
        Icon(
            imageVector = IconPack.IcRefresh,
            contentDescription = "ic_refresh",
            modifier = Modifier.size(24.dp)
        )
    }
}