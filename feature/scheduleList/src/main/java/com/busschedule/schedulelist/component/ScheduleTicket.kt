package com.busschedule.schedulelist.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.designsystem.component.WidthSpacer

@Composable
fun ScheduleTicket(ticketColor: Color, holeColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.42f)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val topRectHeight = this.size.height * (2 / 3f)
            val holeHeight = topRectHeight * (0.14f)
            drawRoundRect(
                color = ticketColor,
                size = Size(width = this.size.width, height = this.size.height),
                cornerRadius = CornerRadius(12f),
            )
            drawLine(
                color = Color.White,
                start = Offset.Zero.copy(y = topRectHeight),
                end = Offset(x = this.size.width, y = topRectHeight),
                strokeWidth = 10f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 40f), 10f)
            )
            drawCircle(
                color = holeColor,
                radius = holeHeight,
                center = Offset(x = 0f, y = topRectHeight)
            )
            drawCircle(
                color = holeColor,
                radius = holeHeight,
                center = Offset(x = this.size.width, y = topRectHeight)
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .weight(2 / 3f)
                .fillMaxWidth()) {
                Text(
                    text = "스케줄 이름",
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp, top = 20.dp)
                )
                Text(
                    text = "버스 정류장 이름",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 26.dp, end = 9.dp)
                )
            }
            Row(
                modifier = Modifier
                    .weight(1 / 3f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(text = "306 (5분) 2정거장")
                    WidthSpacer(width = 8.dp)
                    Text(text = "306 (5분) 2정거장")
                }
                Row {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "ic_edit")
                    core.designsystem.component.WidthSpacer(width = 10.dp)
                    Icon(imageVector = Icons.Rounded.Close, contentDescription = "ic_close")
                }
            }
        }
    }
}
