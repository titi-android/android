package com.busschedule.schedulelist.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.busschedule.model.ArrivingBus
import com.busschedule.model.BusStopInfo
import com.busschedule.model.constant.BusType
import com.busschedule.schedulelist.model.ScheduleUI
import com.busschedule.util.ext.noRippleClickable
import com.busschedule.util.ext.toFormatKrTime
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.dialog.CloseDialog
import core.designsystem.shadow.scheduleShadow
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.svg.myiconpack.IcEdit
import core.designsystem.svg.myiconpack.IcForwardArrow2
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcOffnotify
import core.designsystem.svg.myiconpack.ImageBusOfTicket
import core.designsystem.theme.Background
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mBody
import core.designsystem.theme.mBody2
import core.designsystem.theme.mFooter
import core.designsystem.theme.sbTitle2
import core.designsystem.theme.sbTitle4

@Composable
fun ScheduleTicket(
    holeColor: Color = Color.Transparent,
    schedule: ScheduleUI = ScheduleUI(),
    changeNotifyState: () -> Unit = {},
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    changeBusStopStateOfNotify: (String, String, Int) -> Unit = { _, _, _ -> },
) {
    val icNotify = if (schedule.getAlarm()) MyIconPack.IcNotify else MyIconPack.IcOffnotify
    var isShowCloseDialog by remember { mutableStateOf(false) }
    if (isShowCloseDialog) {
        CloseDialog(
            title = "'스케줄 이름'의 일정을 삭제하시겠습니까?",
            content = "삭제시, 복구 되지 않습니다!",
            onDismissRequest = { isShowCloseDialog = false }) {
            onDelete()
        }
    }
    val busStopInfos = schedule.busStopInfos.take(4)
    var curStep by remember { mutableIntStateOf(0) }
    val ticketColors =
        if (schedule.busStopInfos[curStep].busInfos.isEmpty()) BusType.지정
        else BusType.find(schedule.busStopInfos[curStep].busInfos[0].routetp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.42f)
            .scheduleShadow(
                color = Color(0x2E000000),
                borderRadius = 10.dp,
                blurRadius = 1.dp,
                spread = 1.dp
            )
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val topRectHeight = this.size.height * (2.1f / 3f)
            val holeHeight = topRectHeight * (0.14f)
            drawRoundRect(
                color = ticketColors.color,
                size = Size(width = this.size.width, height = this.size.height),
                cornerRadius = CornerRadius(25f),
            )
            drawLine(
                color = Background,
                start = Offset.Zero.copy(y = topRectHeight),
                end = Offset(x = this.size.width, y = topRectHeight),
                strokeWidth = 5f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 30f), 10f)
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
            imageVector = MyIconPack.ImageBusOfTicket,
            contentDescription = "image_bus_of_ticket",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset((-25).dp),
            alpha = 0.15f
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(2.1f / 3f)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 10.dp, top = 10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = schedule.name,
                        color = TextWColor,
                        style = sbTitle2,
                        modifier = Modifier.weight(1f),
                    )
                    Row() {
                        Icon(
                            imageVector = icNotify,
                            contentDescription = "ic_notify",
                            tint = TextWColor,
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable { changeNotifyState() }
                        )
                        WidthSpacer(width = 20.dp)
                        Icon(
                            imageVector = MyIconPack.IcEdit,
                            contentDescription = "ic_edit",
                            tint = TextWColor,
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable { onEdit() }
                        )
                        WidthSpacer(width = 20.dp)
                        Icon(
                            imageVector = MyIconPack.IcClose,
                            contentDescription = "ic_close",
                            tint = TextWColor,
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable { isShowCloseDialog = true }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    busStopInfos.forEachIndexed { index, busStopInfo ->
                        BusStopTextBox(
                            contentColor = ticketColors.colorT1,
                            step = if (index == 0) "출발" else "환승",
                            busStop = busStopInfo.busStopName,
                            isCurrentStep = curStep == index
                        ) {
                            // TODO:
                            changeBusStopStateOfNotify(schedule.id.toString(), schedule.name, index)
                            curStep = index
                        }
                        Icon(
                            imageVector = MyIconPack.IcForwardArrow2,
                            contentDescription = "ic_next",
                            modifier = Modifier.size(16.dp),
                            tint = TextWColor
                        )
                    }
                    BusStopTextBox(
                        contentColor = ticketColors.colorT1,
                        step = "도착",
                        busStop = schedule.desBusStopName,
                        isCurrentStep = curStep == busStopInfos.size
                    ) {}

                }

            }
            ArrivingBusContainer(busInfo = busStopInfos[curStep].busInfos, ticketColors = ticketColors)
        }
    }
}

@Composable
fun RowScope.BusStopTextBox(
    contentColor: Color,
    step: String,
    isCurrentStep: Boolean,
    busStop: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .noRippleClickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .isCurrentStep(isCurrentStep)
                .padding(horizontal = 10.5.dp, vertical = 2.dp)
        ) {
            Text(
                text = step,
                style = mFooter,
                color = if (isCurrentStep) contentColor else TextWColor,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        HeightSpacer(height = 4.dp)
        Text(text = busStop, style = sbTitle4, color = TextWColor, overflow = TextOverflow.Ellipsis)
    }
}

fun Modifier.isCurrentStep(isCurrentStep: Boolean) =
    if (isCurrentStep) this.background(TextWColor)
    else this.border(width = 1.dp, color = TextWColor, shape = RoundedCornerShape(20.dp))

@Composable
fun ColumnScope.ArrivingBusContainer(
    busInfo: List<ArrivingBus>,
    ticketColors: BusType,
) {
    Row(
        modifier = Modifier
            .weight(1 / 3f)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (busInfo.isEmpty()) {
            Text(text = "도착 예정인 버스가 없습니다.", style = mBody, color = TextWColor)
            return@Row
        }
        busInfo.forEach { busInfo ->
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(color = TextWColor)) {
                    append("${busInfo.routeno} ")
                }
                withStyle(SpanStyle(color = ticketColors.colorT1)) {
                    append("(${busInfo.arrtime.toFormatKrTime()}) ")
                }
                withStyle(mBody2.copy(ticketColors.colorT2).toSpanStyle()) {
                    append("${busInfo.arrprevstationcnt}정거장")
                }
            }, style = mBody)
            WidthSpacer(width = 8.dp)
        }

    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun ScheduleTicketPreview() {
    val dummySchedules = ScheduleUI(
        id = 1,
        name = "출근 버스",
        days = listOf("월", "화", "수", "목", "금"),
        startTime = "07:30",
        endTime = "08:30",
        busStopInfos = listOf(
            BusStopInfo(
                busStopName = "강남역",
                busInfos = listOf(
                    ArrivingBus(
                        arrprevstationcnt = 2,
                        arrtime = 300,
                        nodeid = "1001",
                        nodenm = "강남역",
                        routeid = "R100",
                        routeno = "740",
                        routetp = "간선",
                        vehicletp = "저상버스"
                    ),
                    ArrivingBus(
                        arrprevstationcnt = 5,
                        arrtime = 600,
                        nodeid = "1002",
                        nodenm = "강남역",
                        routeid = "R101",
                        routeno = "341",
                        routetp = "지선",
                        vehicletp = "일반버스"
                    )
                )
            ),
            BusStopInfo(
                busStopName = "역삼역",
                busInfos = listOf(
                    ArrivingBus(
                        arrprevstationcnt = 3,
                        arrtime = 400,
                        nodeid = "1003",
                        nodenm = "역삼역",
                        routeid = "R102",
                        routeno = "146",
                        routetp = "간선",
                        vehicletp = "일반버스"
                    ),
                    ArrivingBus(
                        arrprevstationcnt = 6,
                        arrtime = 700,
                        nodeid = "1004",
                        nodenm = "역삼역",
                        routeid = "R103",
                        routeno = "3412",
                        routetp = "지선",
                        vehicletp = "저상버스"
                    )
                )
            ),
            BusStopInfo(
                busStopName = "삼성역",
                busInfos = listOf(
                    ArrivingBus(
                        arrprevstationcnt = 1,
                        arrtime = 200,
                        nodeid = "1005",
                        nodenm = "삼성역",
                        routeid = "R104",
                        routeno = "500-2",
                        routetp = "광역",
                        vehicletp = "좌석버스"
                    ),
                    ArrivingBus(
                        arrprevstationcnt = 4,
                        arrtime = 550,
                        nodeid = "1006",
                        nodenm = "삼성역",
                        routeid = "R105",
                        routeno = "1100",
                        routetp = "광역",
                        vehicletp = "일반버스"
                    )
                )
            ),
            BusStopInfo(
                busStopName = "선릉역",
                busInfos = listOf(
                    ArrivingBus(
                        arrprevstationcnt = 2,
                        arrtime = 350,
                        nodeid = "1007",
                        nodenm = "선릉역",
                        routeid = "R106",
                        routeno = "8100",
                        routetp = "광역",
                        vehicletp = "좌석버스"
                    ),
                    ArrivingBus(
                        arrprevstationcnt = 5,
                        arrtime = 650,
                        nodeid = "1008",
                        nodenm = "선릉역",
                        routeid = "R107",
                        routeno = "301",
                        routetp = "간선",
                        vehicletp = "일반버스"
                    )
                )
            )
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScheduleTicket(
            schedule = dummySchedules
        )
    }
}

