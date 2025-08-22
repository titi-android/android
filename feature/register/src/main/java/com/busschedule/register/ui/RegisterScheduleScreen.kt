package com.busschedule.register.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.model.BusInfo
import com.busschedule.model.DayOfWeekUi
import com.busschedule.model.constant.TransitConst
import com.busschedule.register.RegisterScheduleViewModel
import com.busschedule.register.component.BusBox
import com.busschedule.register.component.NotifyIcon
import com.busschedule.register.component.ScheduleNameTextField
import com.busschedule.register.component.SelectTransitDialog
import com.busschedule.register.component.TransitCard
import com.busschedule.register.component.WhiteContentBox
import com.busschedule.register.constant.TimePickerType
import com.busschedule.register.model.ScheduleRegister
import com.busschedule.register.model.TransitPointType
import com.busschedule.register.util.convertTimePickerToUiTime
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.DayOfWeekCard
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.dialog.TitleDialog
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcBus
import core.designsystem.svg.myiconpack.IcMinus
import core.designsystem.svg.myiconpack.IcPlus
import core.designsystem.theme.Background
import core.designsystem.theme.Primary
import core.designsystem.theme.Primary2
import core.designsystem.theme.TextColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.sbTitle2
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScheduleScreen(
    appState: ApplicationState,
    viewModel: RegisterScheduleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.registerBusScheduleUiState.collectAsStateWithLifecycle(ScheduleRegister())
    val lastTransitCardUI by viewModel.lastTransitCardUIInfos.collectAsStateWithLifecycle()
    var isShowTempSaveScheduleDialog by remember { mutableStateOf(false) }
    var isShowSelectRegisterTypeDialog by remember { mutableStateOf(false) }
    val changeSelectRegisterTypeDialogState: (Boolean) -> Unit =
        remember { { isShowSelectRegisterTypeDialog = it } }

    BackHandler {
        /*
        if ((viewModel.isRouteInfoNotEmpty() || uiState.isNotEmpty()) && viewModel.isUpdateSchedule().not()) { isShowTempSaveScheduleDialog = true }
        else { appState.popBackStack() }
         */
        appState.popBackStack()
    }

    LaunchedEffect(Unit) {
        when (appState.isBackFromSubway()) {
            TransitConst.BUS.name -> {
                val busStopInfo = appState.getSavedDataFromBus()
                viewModel.addBusInfo(busStopInfo)
            }

            TransitConst.SUBWAY.name -> {
                val subwayInfo = appState.getSavedDataFromSubway()
                viewModel.addSubwayInfo(subwayInfo)
            }

            else -> Log.e("daeyoung", "RegisterScheduleScreen: null")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        if (isShowTempSaveScheduleDialog) {
            TitleDialog(
                title = "작성 중인 스케줄을 저장할까요?",
                leftBtnText = "취소",
                rightBtnText = "임시 저장",
                onDismissRequest = { isShowTempSaveScheduleDialog = false },
                onNotComplete = { appState.popBackStack() },
                onComplete = { /*viewModel.fetchInsertTempSchedule {appState.popBackStack()} */ })
        }
        if (isShowSelectRegisterTypeDialog) {
            SelectTransitDialog(
                onDismissRequest = { isShowSelectRegisterTypeDialog = false },
                // TODO: navigateToSelectRegion() 에 들어가는 매개변수 목적 파악
                navigateToSelectRegion = { appState.navigateToSelectRegion(viewModel.getCurrentFocusTransitCard()) },
                navigateToSubway = { appState.navigateToSelectSubway() },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            BackArrowAppBar(title = "스케줄 등록하기") {
                /*
                if ((viewModel.isRouteInfoNotEmpty() || uiState.isNotEmpty()) && viewModel.isUpdateSchedule().not()) { isShowTempSaveScheduleDialog = true }
                else { appState.popBackStack() }

                 */
            }
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                HeightSpacer(height = 32.dp)
                ScheduleNameTextField(
                    value = uiState.name,
                    onValueChange = { viewModel.updateScheduleName(it) },
                    placeholder = "스케줄"
                )
                HeightSpacer(height = 32.dp)
                NotifyArea(
                    dayOfWeeks = uiState.dayOfWeeks,
                    startTime = uiState.startTime,
                    endTime = uiState.endTime,
                    updateStartTime = { viewModel.updateStartTime(it.convertTimePickerToUiTime()) },
                    updateEndTime = { viewModel.updateEndTime(it.convertTimePickerToUiTime()) },
                    isNotify = uiState.isNotify
                ) {
                    viewModel.updateIsNotify()
                }

                if (viewModel.transitCardUIInfos.isEmpty()) {
                    HeightSpacer(16.dp)
                    TransitCard(
                        onInitClick = {
                            changeSelectRegisterTypeDialogState(it)
                            viewModel.setCurrentFocusTransitCard(RegisterScheduleViewModel.START_NOT_INIT_TRANSIT_CARD_ID)
                        },
                        type = TransitPointType.START,
                    )
                }

                if (viewModel.transitCardUIInfos.isNotEmpty() && lastTransitCardUI.isEmpty().not()) {
                    TransferRow {
                        if (viewModel.transitCardUIInfos.size == RegisterScheduleViewModel.LAST_TRANSIT_CARD_ID) return@TransferRow
                        changeSelectRegisterTypeDialogState(true)
                        viewModel.setCurrentFocusTransitCard(viewModel.transitCardUIInfos.size)
                    }
                } else {
                    HeightSpacer(16.dp)
                }

                viewModel.transitCardUIInfos.forEachIndexed { index, transitInfo ->
                    TransitCard(
                        id = index + 1,
                        type = if (index == 0) TransitPointType.START else TransitPointType.TRANSFER,
                        transitCardUI = transitInfo,
                        onEditClick = { isNotInit ->
                            if (isNotInit.not()) {
                                changeSelectRegisterTypeDialogState(true)
                                viewModel.setCurrentFocusTransitCard(index)
                            }
                        },
                        onRemoveClick = { viewModel.removeTransitCard(it) }
                    )
                    HeightSpacer(height = 16.dp)
                    /*
                    RegionArea(
                        title = if (index == 0) "출발" else "환승",
                        region = busStopInfoUI.region,
                        navigateRegionScreen = { appState.navigateToSelectRegion(busStopInfoUI.getID()) },
                        busStop = busStopInfoUI.busStop,
                        buses = busStopInfoUI.getBuses(),
                        isRemoveRegionArea = viewModel.routeInfos.size >= 2,
                        removeRegionArea = { viewModel.removeBusStopInfoUI(busStopInfoUI.getID()) },
                        deleteBus = { busStopInfoUI.remove(it) }) {
                        Log.d("daeyoung", "busStopInfoUI: $busStopInfoUI")
                        appState.navigateToSelectBusStop(busStopInfoUI.asBusStopInfo())
                    }

                     */
                }
                TransitCard(
                    type = TransitPointType.END,
                    transitCardUI = lastTransitCardUI,
                    onInitClick = {
                        changeSelectRegisterTypeDialogState(it)
                        viewModel.setCurrentFocusTransitCard(RegisterScheduleViewModel.LAST_TRANSIT_CARD_ID)
                    },
                    onEditClick = {
                        changeSelectRegisterTypeDialogState(true)
                        viewModel.setCurrentFocusTransitCard(RegisterScheduleViewModel.LAST_TRANSIT_CARD_ID)
                    },
                )
            }
            MainBottomButton(text = "완료") {
                viewModel.putOrPostSchedule(
                    navigateToScheduleList = { appState.navigateToScheduleList() },
                ) { appState.showToastMsg(it) }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifyArea(
    dayOfWeeks: List<DayOfWeekUi> = emptyList(),
    startTime: String,
    endTime: String,
    updateStartTime: (TimePickerState) -> Unit,
    updateEndTime: (TimePickerState) -> Unit,
    isNotify: Boolean = false,
    onNotifyClick: (Boolean) -> Unit,
) {
    Column {
        Text(text = "알림", style = sbTitle2.copy(TextColor))
        HeightSpacer(height = 12.dp)
        MultiSelectDayOfWeek(dayOfWeeks = dayOfWeeks)
        HeightSpacer(height = 16.dp)
        SelectRangeTimeArea(
            startTime = startTime,
            endTime = endTime,
            updateStartTime = { updateStartTime(it) }) {
            updateEndTime(it)
        }
        HeightSpacer(height = 16.dp)
        NotifyIcon(isCheck = isNotify) {
            onNotifyClick(it)
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegionArea(
    title: String = "출발",
    region: String,
    navigateRegionScreen: () -> Unit,
    busStop: String,
    buses: List<BusInfo>,
    isRemoveRegionArea: Boolean = false,
    removeRegionArea: () -> Unit = {},
    deleteBus: (String) -> Unit,
    navigateBusStopScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, end = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = title, style = sbTitle2.copy(TextColor))
            if (isRemoveRegionArea) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = TextWColor,
                        contentColor = Primary
                    ), onClick = { removeRegionArea() }) {
                    Icon(
                        imageVector = MyIconPack.IcMinus,
                        contentDescription = "ic_plus",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        HeightSpacer(height = 14.dp)
        WhiteContentBox(text = region.ifBlank { "도시(지역)" }) { navigateRegionScreen() }
        HeightSpacer(height = 14.dp)
        WhiteContentBox(text = busStop.ifBlank { "버스 정류장" }) { navigateBusStopScreen() }
        HeightSpacer(height = 14.dp)
        WhiteContentBox(text = "버스 번호") {}
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buses.forEach { bus ->
                BusBox(
                    icon = MyIconPack.IcBus,
                    name = bus.name,
                    type = bus.type,
                )
            }
        }
    }
}

@Composable
fun ArriveArea(
    region: String,
    navigateRegionScreen: () -> Unit,
    busStop: String,
    navigateBusStopScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, end = 8.dp)
    ) {
        Text(text = "도착", style = sbTitle2.copy(TextColor))
        HeightSpacer(height = 14.dp)
        WhiteContentBox(text = region.ifBlank { "도시(지역)" }) { navigateRegionScreen() }
        HeightSpacer(height = 14.dp)
        WhiteContentBox(text = busStop.ifBlank { "버스 정류장" }) { navigateBusStopScreen() }
    }
}

@Composable
fun TransferRow(plusTransferArea: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "환승", style = sbTitle2.copy(TextColor))
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = TextWColor,
                contentColor = Primary
            ), onClick = { plusTransferArea() }) {
            Icon(
                imageVector = MyIconPack.IcPlus,
                contentDescription = "ic_plus",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.SelectRangeTimeArea(
    startTime: String,
    endTime: String,
    updateStartTime: (TimePickerState) -> Unit,
    updateEndTime: (TimePickerState) -> Unit,
) {
    var isShowTimePickerDialog by remember { mutableStateOf(TimePickerType.NONE) }
    val color = Color(0xFF808991)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { isShowTimePickerDialog = TimePickerType.START_TIME }
                .padding(start = 16.dp, top = 14.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = startTime, color = color)
        }
        WidthSpacer(width = 12.dp)
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { isShowTimePickerDialog = TimePickerType.END_TIME }
                .padding(start = 16.dp, top = 14.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = endTime, color = color)
        }
    }
    when (isShowTimePickerDialog) {
        TimePickerType.START_TIME -> {
            TimePickerFieldToModal(
                onDismiss = { isShowTimePickerDialog = TimePickerType.NONE },
                onConfirm = { updateStartTime(it) },
            )
        }

        TimePickerType.END_TIME -> {
            TimePickerFieldToModal(
                onDismiss = { isShowTimePickerDialog = TimePickerType.NONE },
                onConfirm = { updateEndTime(it) },
            )
        }

        TimePickerType.NONE -> {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerFieldToModal(
    onDismiss: (Boolean) -> Unit,
    onConfirm: (TimePickerState) -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Dialog(
        onDismissRequest = { onDismiss(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = TextWColor
                ),
            color = Background
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = TextWColor,
                        selectorColor = Primary,
                        clockDialUnselectedContentColor = Primary,
                        timeSelectorSelectedContainerColor = Color.White,
                        timeSelectorUnselectedContainerColor = Color.White,
                        timeSelectorSelectedContentColor = Primary2,
                        timeSelectorUnselectedContentColor = Primary2,
                    )
                )

                Row() {
                    TextButton(modifier = Modifier.weight(1f), onClick = { onDismiss(false) }) {
                        Text(text = "Cancel", color = Primary)
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onConfirm(timePickerState)
                            onDismiss(false)
                        }) {
                        Text(text = "Ok", color = Primary)
                    }
                }
            }
        }
    }
}

@Composable
fun MultiSelectDayOfWeek(
    dayOfWeeks: List<DayOfWeekUi> = emptyList(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        dayOfWeeks.forEach { day ->
            DayOfWeekCard(
                text = day.dayOfWeek.value,
                isSelected = day.isSelected,
                isShadow = false
            ) {
                day.updateSelected(!day.isSelected)
            }
        }
    }
}