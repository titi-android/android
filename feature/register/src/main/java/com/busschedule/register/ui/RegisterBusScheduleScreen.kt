package com.busschedule.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.constant.TimePickerType
import com.busschedule.register.entity.ScheduleRegister
import com.busschedule.register.util.convertTimePickerToUiTime
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState
import core.designsystem.component.WidthSpacer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBusScheduleScreen(
    appState: ApplicationState,
    registerBusScheduleViewModel: RegisterBusScheduleViewModel = hiltViewModel(),
) {

    val registerBusScheduleUiState by
    registerBusScheduleViewModel.registerBusScheduleUiState.collectAsStateWithLifecycle(
        ScheduleRegister()
    )

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = registerBusScheduleUiState.name,
            onValueChange = { registerBusScheduleViewModel.setScheduleName(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "스케줄 이름") },
            maxLines = 1
        )
        DatePickerFieldToModal()
        SelectRangeTimeArea(
            startTime = registerBusScheduleUiState.startTime,
            endTime = registerBusScheduleUiState.endTime,
            updateStartTime = { registerBusScheduleViewModel.setStartTime(it.convertTimePickerToUiTime()) }) {
            registerBusScheduleViewModel.setEndTime(it.convertTimePickerToUiTime())
        }
        TextField(
            value = registerBusScheduleUiState.regionName,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            maxLines = 1,
            trailingIcon = {
                Button(onClick = { appState.navigate(Constants.SELECT_REGION_ROUTE) }) {
                    Text(text = "조회")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }),
            placeholder = { Text(text = "도시(지역) 이름") })
        TextField(
            value = registerBusScheduleUiState.busStopName,
            onValueChange = { registerBusScheduleViewModel.setBusStop(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            trailingIcon = {
                Button(onClick = {
                    registerBusScheduleViewModel.fetchCheckBusStop(
                        registerBusScheduleUiState.regionName,
                        registerBusScheduleUiState.busStopName
                    )
                }) {
                    Text(text = "조회")
                }
            },
            supportingText = {
                val supText = registerBusScheduleUiState.busStopSupportingName
                Text(text = supText.resultMsg, color = supText.color)},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }),
            placeholder = { Text(text = "버스정류장 명") })
        TextField(
            value = ""/*TODO*/,
            onValueChange = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            trailingIcon = {
                Button(onClick = { appState.navigate(Constants.SELECT_BUS_ROUTE) }) {
                    Text(text = "조회")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            placeholder = { Text(text = "버스 목록") })
        TextButton(onClick = {
            registerBusScheduleViewModel.fetchPostBusSchedule(registerBusScheduleUiState)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "완료")
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerFieldToModal() {
    var datePickerState = rememberDatePickerState()
    var date = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
//        DatePicker(state = datePickerState)by remember { mutableStateOf("요일") }
    var isShowDatePickerDialog by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = date,
        onValueChange = { },
        label = { Text(text = "요일") },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select date"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(date) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        isShowDatePickerDialog = true
                    }
                }
            }
    )
    if (isShowDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { isShowDatePickerDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    date =
                        "${datePickerState.selectedDateMillis?.let { convertMillisToDate(it) }}"
                    isShowDatePickerDialog = false
                }) { Text(text = "Ok") }
            },
            dismissButton = {
                TextButton(onClick = {
                    isShowDatePickerDialog = false
                }) { Text(text = "Cancel") }
            }) {
            DatePicker(state = datePickerState)
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

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "시작 시간")
        WidthSpacer(width = 8.dp)
        Button(onClick = { isShowTimePickerDialog = TimePickerType.START_TIME }) {
            Text(text = startTime)
        }
        WidthSpacer(weight = 1f)
        Text(text = "종료 시간")
        WidthSpacer(width = 8.dp)
        Button(onClick = { isShowTimePickerDialog = TimePickerType.END_TIME }) {
            Text(text = endTime)
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
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)

                Row() {
                    TextButton(modifier = Modifier.weight(1f), onClick = { onDismiss(false) }) {
                        Text(text = "Cancel")
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onConfirm(timePickerState)
                            onDismiss(false)
                        }) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}

