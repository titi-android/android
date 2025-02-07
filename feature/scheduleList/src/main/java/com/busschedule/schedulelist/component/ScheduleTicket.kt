package com.busschedule.schedulelist.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.busschedule.schedulelist.model.BusScheduleUi
import com.busschedule.util.ext.toFormatTime
import core.designsystem.component.WidthSpacer
import core.designsystem.component.dialog.CloseDialog
import core.designsystem.svg.IconPack
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.svg.myiconpack.IcEdit
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcOffnotify
import core.designsystem.svg.myiconpack.ImageBusOfTicket
import core.designsystem.theme.Background
import core.designsystem.theme.BusBlueM1
import core.designsystem.theme.BusBlueM2
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mBody
import core.designsystem.theme.mBody2
import core.designsystem.theme.sbTitle
import core.designsystem.theme.sbTitle4

@Composable
fun ScheduleTicket(
    ticketColor: Color,
    holeColor: Color,
    schedule: BusScheduleUi,
    changeNotifyState: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    val icNotify = if (schedule.getAlarm()) IconPack.IcNotify  else IconPack.IcOffnotify
    var isShowCloseDialog by remember { mutableStateOf(false) }
    if (isShowCloseDialog) {
        CloseDialog(
            title = "'스케줄 이름'의 일정을 삭제하시겠습니까?",
            content = "삭제시, 복구 되지 않습니다!",
            onDismissRequest = { isShowCloseDialog = false }) {
            onDelete()
        }
    }

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
                cornerRadius = CornerRadius(50f),
            )
            drawLine(
                color = Background,
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
        Image(
            imageVector = IconPack.ImageBusOfTicket,
            contentDescription = "image_bus_of_ticket",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset((-25).dp),
            alpha = 0.15f
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(2 / 3f)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 10.dp)
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Text(
                        text = schedule.name,
                        color = TextWColor,
                        style = sbTitle,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 20.dp)
                    )
                    Row(modifier = Modifier.padding(10.dp)) {
                        Icon(
                            imageVector = icNotify,
                            contentDescription = "ic_notify",
                            tint = TextWColor,
                            modifier = Modifier.size(24.dp).clickable { changeNotifyState() }
                        )
                        WidthSpacer(width = 20.dp)
                        Icon(
                            imageVector = IconPack.IcEdit,
                            contentDescription = "ic_edit",
                            tint = TextWColor,
                            modifier = Modifier.size(24.dp)
                        )
                        WidthSpacer(width = 20.dp)
                        Icon(
                            imageVector = IconPack.IcClose,
                            contentDescription = "ic_close",
                            tint = TextWColor,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { isShowCloseDialog = true }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, end = 11.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StartTextBox(bgColor = TextWColor, contentColor = BusBlueM1)
                    WidthSpacer(width = 8.dp)
                    Text(
                        text = schedule.busStopName,
                        style = sbTitle4.copy(TextWColor),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                    )
                }

            }
            Row(
                modifier = Modifier
                    .weight(1 / 3f)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                schedule.busInfos.forEach { busInfo ->
                    Text(text = buildAnnotatedString {
                        withStyle(SpanStyle(color = TextWColor)) {
                            append("${busInfo.routeno} ")
                        }
                        withStyle(SpanStyle(color = BusBlueM1)) {
                            append("(${busInfo.arrtime.toFormatTime()}분) ")
                        }
                        withStyle(mBody2.copy(BusBlueM2).toSpanStyle()) {
                            append("${busInfo.arrprevstationcnt}정거장")
                        }
                    }, style = mBody)
                    WidthSpacer(width = 8.dp)
                }

            }
        }
    }
}

@Composable
fun StartTextBox(bgColor: Color, contentColor: Color) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(bgColor)
            .padding(horizontal = 10.5.dp, vertical = 2.dp)
    ) {
        Text(text = "출발", style = mBody2, color = contentColor, modifier = Modifier.align(Alignment.Center))
    }
}