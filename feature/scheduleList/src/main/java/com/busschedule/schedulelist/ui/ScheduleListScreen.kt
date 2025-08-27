package com.busschedule.schedulelist.ui

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.common.constant.Constants
import com.busschedule.model.DayOfWeekUi
import com.busschedule.schedulelist.ScheduleListViewModel
import com.busschedule.schedulelist.component.RefreshIcon
import com.busschedule.schedulelist.component.ScheduleListAppBar
import com.busschedule.schedulelist.component.ScheduleTicket
import com.busschedule.schedulelist.model.ScheduleListUiState
import com.busschedule.schedulelist.permission.CheckNotifyPermission
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.DayOfWeekCard
import core.designsystem.component.HeightSpacer
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.dialog.TitleDialog
import core.designsystem.component.loading.LoadingOfCoilDialog
import core.designsystem.theme.Background
import kotlinx.coroutines.delay

@Composable
fun ScheduleListScreen(
    appState: ApplicationState,
    viewModel: ScheduleListViewModel = hiltViewModel(),
) {

    val uiState by viewModel.scheduleListUiState.collectAsStateWithLifecycle(
        ScheduleListUiState()
    )
    var isShowBringTempScheduleDialog by remember { mutableStateOf(false) }
    var isRefreshLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    CheckNotifyPermission()
    BackOnPressed()

    LaunchedEffect(Unit) {
        while (true) {
            if (uiState.dayOfWeeks.find { it.isSelected }?.isToday() == true) {
                viewModel.fetchReadTodaySchedules { appState.showToastMsg(it) }
            }
            delay(Constants.MINUTE_1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        if (isShowBringTempScheduleDialog) {
            TitleDialog(
                title = "작성 중인 스케줄을 저장할까요?",
                leftBtnText = "새로 쓰기",
                rightBtnText = "이어서 쓰기",
                onDismissRequest = { isShowBringTempScheduleDialog = false },
                onNotComplete = {
                    viewModel.fetchDeleteTempSchedule {
                        appState.navigateToRegister(dayOfWeek = uiState.getSelectedDayOfWeek())
                    }
                },
                onComplete = {
                    appState.navigateToRegister(
                        isExistTempSchedule = true,
                        dayOfWeek = uiState.getSelectedDayOfWeek()
                    )
                }
            )
        }
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
                viewModel.fetchReadDayOfWeekSchedules(it) { appState.showToastMsg(it) }
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
                            scheduleName = schedule.scheduleName,
                            transit = schedule.ticketUI,
                            destinationName = schedule.destinationName
                        ) {
                            appState.navigateToRegister(schedule.id)
                        }
                        /*
                        TempScheduleTicket(
                            holeColor = Background,
                            schedule = schedule,
                            changeNotifyState = {
                                viewModel.fetchPutScheduleAlarm(
                                    scheduleId = schedule.id,
                                    updateAlarm = {
                                        schedule.updateAlarm()
                                    }
                                ) { appState.showToastMsg(it) }
                            },
                            onEdit = { appState.navigateToRegister(schedule.id) },
                            onDelete = {
                                viewModel.fetchDeleteSchedules(schedule.id) {
                                    appState.showToastMsg(it)
                                }
                            }
                        ) { scheduleId, scheduleName, notifyState ->
                            viewModel.fetchUpdateBusStopStateOfNotify(
                                scheduleId,
                                scheduleName,
                                notifyState
                            )
                        }

                         */
                    }
                }
                RefreshIcon(
                    modifier = Modifier.padding(bottom = 16.dp, end = 16.dp),
                    isLoading = isRefreshLoading
                ) {
                    isRefreshLoading = true
                    viewModel.fetchReadDayOfWeekSchedules(
                        dayOfWeek = uiState.getSelectedDayOfWeek(),
                        changeLoadingState = { isRefreshLoading = false }
                    ) { appState.showToastMsg(it) }
                }
            }

            MainBottomButton(text = "스케줄 등록") {
                /*
                viewModel.fetchIsExistTempSchedule(
                    navigateToRegister = { appState.navigateToRegister(dayOfWeek = uiState.getSelectedDayOfWeek()) }
                ) { isShowBringTempScheduleDialog = true }
                 */
                appState.navigateToRegister(dayOfWeek = uiState.getSelectedDayOfWeek())
            }

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

// 뒤로 가기 두 번 눌렀을 때 앱 종료
@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if(System.currentTimeMillis() - backPressedTime <= 2000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}