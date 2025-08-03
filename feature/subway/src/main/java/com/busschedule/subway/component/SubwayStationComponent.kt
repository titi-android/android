package com.busschedule.subway.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.busschedule.subway.ui.SelectStation
import com.busschedule.subway.ui.StationDirection
import core.designsystem.shadow.titiShadow
import core.designsystem.theme.TextBoxDis

@Composable
fun SubwayStationComponent(isSelected: SelectStation = SelectStation.NOT, stationDirection: StationDirection = StationDirection.NONE, selectedColor: Color) {



    val dynamicSize = if (isSelected in listOf(SelectStation.START, SelectStation.END)) 28.dp else 20.dp
    val dynamicOffset = if (isSelected in listOf(SelectStation.START, SelectStation.END)) (-4).dp else 0.dp
    val stationLineWidth = 20.dp

    val stationColor = when (isSelected) {
        SelectStation.NOT -> TextBoxDis
        else -> selectedColor
    }

    val stationLineColor = when (isSelected) {
        SelectStation.START -> {
            if (stationDirection == StationDirection.UP) selectedColor to TextBoxDis
            else TextBoxDis to selectedColor
        }
        SelectStation.END -> {
            if (stationDirection == StationDirection.DOWN) selectedColor to TextBoxDis
            else TextBoxDis to selectedColor
        }
        SelectStation.RANGE -> selectedColor to selectedColor
        SelectStation.NOT -> TextBoxDis to TextBoxDis
    }

    Box(
        modifier = Modifier
            .height(32.dp+stationLineWidth)
            .drawBehind {
                drawLine(
                    color = stationLineColor.first,
                    start = Offset(stationLineWidth.toPx()/2, 0f),
                    end = Offset(stationLineWidth.toPx()/2, size.height/2),
                    strokeWidth = 10.dp.toPx()
                )
                drawLine(
                    color = stationLineColor.second,
                    start = Offset(stationLineWidth.toPx()/2, size.height/2),
                    end = Offset(stationLineWidth.toPx()/2, size.height),
                    strokeWidth = 10.dp.toPx()
                )
            }
    ) {
        Box(
            modifier = Modifier
//                .subwayShadow(isSelected)
                .align(Alignment.Center)
                .offset(x = dynamicOffset)
                .size(dynamicSize)
                .clip(CircleShape)
                .background(stationColor)
                .padding(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .size(8.dp)
                    .background(Color(0xFFFFFFFF))
            )
        }
    }
}

fun Modifier.subwayShadow(isSelected: SelectStation = SelectStation.NOT): Modifier {
    if (isSelected == SelectStation.NOT) return this
    return this.titiShadow(blurRadius = 8.dp, color = Color(0x52000000))
}