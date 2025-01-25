package com.busschedule.schedulelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.domain.model.response.schedule.BusSchedule
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@Composable
fun ScheduleListScreen(
    appState: ApplicationState,
    scheduleListViewModel: ScheduleListViewModel = hiltViewModel(),
) {

    val scheduleListUiState by scheduleListViewModel.scheduleListUiState.collectAsStateWithLifecycle()
    var input by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        listOf(
            async { scheduleListViewModel.initFCMToken() },
            async { scheduleListViewModel.fetchReadTodaySchedules() }
        ).awaitAll()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF525252))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                trailingIcon = {
                    Button(onClick = { scheduleListViewModel.fetchReadDayOfWeekSchedules(input) }) {
                        Text(text = "조회")
                    }
                },
                placeholder = { Text(text = "요일 선택(기본값 현재 요일)") })
        }
        val lazyListState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp),
            state = lazyListState,
            contentPadding = PaddingValues(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = scheduleListUiState, key = { it.id }) {
                TempScheduleCard(backgroundColor = Color.White, schedule = it)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { appState.navigate(Constants.REGISTER_BUS_SCHEDULE_ROUTE) },
                modifier = Modifier.weight(3f)
            ) {
                Text(text = "스케줄 등록")
            }
            WidthSpacer(width = 8.dp)
            Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Text(text = "설정")
            }
        }
    }


}


@Composable
fun TempScheduleCard(backgroundColor: Color, schedule: BusSchedule) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row {
            Text(text = schedule.name, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.Edit, contentDescription = "ic_edit")
            WidthSpacer(width = 4.dp)
            Icon(imageVector = Icons.Default.Delete, contentDescription = "ic_delete")
        }
        HeightSpacer(height = 4.dp)
        // TODO: 백엔드가 데이터 수정하면 변경
        Text(text = schedule.busStopName)
        HeightSpacer(height = 4.dp)
//        Text(text = "${schedule.busInfos[0].routeno}, ${schedule.busInfos[1].routeno}")
    }
}