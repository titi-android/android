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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer

data class TempSchedule(
    val name: String,
    val busStop: String,
    val firstBus: String,
    val secondBus: String,
)

@Composable
fun ScheduleListScreen(appState: ApplicationState) {
    val schedules = listOf(
        TempSchedule("스케줄 1", "정류장 1", "버스 1", "버스 2"),
        TempSchedule("스케줄 2", "정류장 2", "버스 3", "버스 4"),
        TempSchedule("스케줄 3", "정류장 3", "버스 5", "버스 6"),
        TempSchedule("스케줄 4", "정류장 4", "버스 7", "버스 8"),
        TempSchedule("스케줄 5", "정류장 5", "버스 9", "버스 10"),
        TempSchedule("스케줄 6", "정류장 6", "버스 11", "버스 12"),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF525252))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "요일 선택(기본값 현재 요일)")
            }
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
            items(items = schedules, key = { it.name }) {
                TempScheduleCard(backgroundColor = Color.White, schedule = it)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { appState.navigate(Constants.REGISTER_BUS_SCHEDULE_ROUTE) }, modifier = Modifier.weight(3f)) {
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
fun TempScheduleCard(backgroundColor: Color, schedule: TempSchedule) {
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
        Text(text = schedule.busStop)
        HeightSpacer(height = 4.dp)
        Text(text = "${schedule.firstBus}, ${schedule.secondBus}")
    }
}