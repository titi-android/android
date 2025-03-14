package com.busschedule.schedulelist.ui

import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.common.constant.Constants
import com.busschedule.schedulelist.ScheduleListViewModel
import com.busschedule.schedulelist.component.ScheduleListAppBar
import com.busschedule.schedulelist.component.ScheduleTicket
import com.busschedule.schedulelist.model.ScheduleListUiState
import com.busschedule.schedulelist.permission.CheckNotifyPermission
import com.busschedule.util.entity.DayOfWeekUi
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.DayOfWeekCard
import core.designsystem.component.HeightSpacer
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.loading.LoadingOfCoilDialog
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcRefresh
import core.designsystem.theme.Background
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import kotlinx.coroutines.delay

@Composable
fun ScheduleListScreen(
    appState: ApplicationState,
    scheduleListViewModel: ScheduleListViewModel = hiltViewModel(),
) {

    val uiState by scheduleListViewModel.scheduleListUiState.collectAsStateWithLifecycle(
        ScheduleListUiState()
    )
    CheckNotifyPermission()

    LaunchedEffect(Unit) {
        while (true) {
            if (uiState.dayOfWeeks.find { it.isSelected }?.isToday() == true) {
                scheduleListViewModel.fetchReadTodaySchedules { appState.showToastMsg(it) }
            }
            delay(Constants.MINUTE_1)
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            HeightSpacer(height = 6.dp)
            ScheduleListAppBar { appState.navigateToSetting() }
            HeightSpacer(height = 16.dp)
            DayOfWeekSelectArea(dayOfWeekUi = uiState.dayOfWeeks) {
                scheduleListViewModel.fetchReadDayOfWeekSchedules(it) { appState.showToastMsg(it) }
            }
            Box(modifier = Modifier.weight(1f)) {
                val lazyListState = rememberLazyListState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp),
                    state = lazyListState,
                    contentPadding = PaddingValues(top = 8.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = uiState.schedules, key = { it.id }) { schedule ->
                        ScheduleTicket(
                            holeColor = Background,
                            schedule = schedule,
                            changeNotifyState = {
                                scheduleListViewModel.fetchPutScheduleAlarm(
                                    scheduleId = schedule.id,
                                    updateAlarm = {
                                        schedule.updateAlarm()
                                    }
                                ) { appState.showToastMsg(it) }
                            },
                            onEdit = { appState.navigateToRegister(schedule.id) },
                            onDelete = {scheduleListViewModel.fetchDeleteSchedules(schedule.id) {
                                appState.showToastMsg(it)
                            }}
                        ) { scheduleId, scheduleName, notifyState ->
                            scheduleListViewModel.fetchUpdateBusStopStateOfNotify(scheduleId, scheduleName, notifyState)
                        }
                    }
                }
                RefreshIcon(modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)) {
                    scheduleListViewModel.fetchReadDayOfWeekSchedules(uiState.getSelectedDayOfWeek()) {
                        appState.showToastMsg(
                            it
                        )
                    }
                }
            }

            MainBottomButton(text = "스케줄 등록") { appState.navigate(com.busschedule.navigation.Route.RegisterGraph.RegisterSchedule()) }
        }
        if (uiState.isLoading) {
            LoadingOfCoilDialog()
        }
    }

}


@Composable
fun DayOfWeekSelectArea(dayOfWeekUi: List<DayOfWeekUi>, requestDaySchedule: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
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
fun BoxScope.RefreshIcon(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .align(Alignment.BottomEnd),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Primary,
            contentColor = TextWColor
        ), onClick = { onClick() }) {
        Icon(
            imageVector = MyIconPack.IcRefresh,
            contentDescription = "ic_refresh",
            modifier = Modifier.size(24.dp)
        )
    }
}