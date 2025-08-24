package com.busschedule.schedulelist.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.busschedule.model.constant.BusType
import com.busschedule.schedulelist.model.DestinationTransitInfo
import com.busschedule.schedulelist.model.TransitTicketUI
import com.busschedule.util.ext.noRippleClickable
import com.busschedule.util.ext.toFormatKrTime
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcForwardArrow2
import core.designsystem.theme.BusGreen
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mBody
import core.designsystem.theme.mBody2
import core.designsystem.theme.normal10sp
import core.designsystem.theme.rTextBtn2
import core.designsystem.theme.sbTitle4


@Composable
fun ScheduleTicket(
    scheduleName: String = "출근",
    transit: List<TransitTicketUI>,
    destinationName: String = "집"
) {

    val transitList = transit.take(4)
    var curStep by remember { mutableIntStateOf(0) }

    val ticketColors =
        when (transitList[curStep]) {
            is TransitTicketUI.SubwayTicketUI -> {
                BusType.지정
            }

            is TransitTicketUI.BusTicketUI -> {
                if (transitList[curStep].getDestinationTransits().isEmpty()) BusType.지정
                else BusType.find(transitList[curStep].getDestinationTransits()[0].routeType)
            }
        }

    val rounded = 16.dp
    Column(modifier = Modifier.fillMaxWidth()) {
        // 상단 내용, 출근
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = rounded, topEnd = rounded))
                .drawBehind { drawRoundRect(color = ticketColors.color) }
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = scheduleName, style = rTextBtn2.copy(color = TextWColor))
        }
        // 중간 내용
        TicketMiddleContainer(
            color = ticketColors.color,
            transit = transitList,
            destinationName = destinationName
        )
        // 하단 내용
        TicketBottomContainer(
            color = ticketColors.color,
            colorT1 = ticketColors.colorT1,
            colorT2 = ticketColors.colorT2,
            destinationTransitInfo = transitList[curStep].getDestinationTransits()
        )
    }
}

@Composable
fun TicketMiddleContainer(
    rounded: Dp = 16.dp,
    color: Color,
    transit: List<TransitTicketUI>,
    destinationName: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-1).dp)
            .zIndex(1f)
    ) {
        val bottomRoundCornerShape =
            RoundedCornerShape(bottomStart = rounded, bottomEnd = rounded)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(bottomRoundCornerShape) // Clip to prevent border from drawing outside
                .border(
                    color = color,
                    width = 1.dp,
                    shape = bottomRoundCornerShape
                )
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            transit.forEachIndexed { index, t ->
                TicketTransitContent(
                    contentColor = BusGreen,
                    step = if (index == 0) "출발" else "환승",
                    startPoint = t.getStartingPoint(),
                    isCurrentStep = false
                ) {
                    // TODO:
//                        changeBusStopStateOfNotify(schedule.id.toString(), schedule.name, index)
//                        curStep = index
                }
                Icon(
                    imageVector = MyIconPack.IcForwardArrow2,
                    contentDescription = "ic_next",
                    modifier = Modifier.size(16.dp),
                    tint = color
                )
            }
            TicketTransitContent(
                contentColor = TextBoxDis,
                step = "도착",
                startPoint = destinationName,
                isCurrentStep = false
            ) {}
        }
        WhiteDivider(modifier = Modifier.align(Alignment.BottomCenter), cornerRadius = 16.dp)
        DashedDivider(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = color,
            cornerRadius = 16.dp
        )
    }
}

@Composable
fun TicketBottomContainer(
    rounded: Dp = 16.dp,
    color: Color,
    colorT1: Color,
    colorT2: Color,
    destinationTransitInfo: List<DestinationTransitInfo>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-2).dp)
    ) {
        val fullRoundCornerShape = RoundedCornerShape(rounded)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    color = color,
                    width = 1.dp,
                    shape = fullRoundCornerShape
                )
                .background(Color.White, shape = fullRoundCornerShape)
//                .drawBehind { drawLine(color = BusIcBlue, start = Offset.Zero, end = size.center) }
                .padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (destinationTransitInfo.isEmpty()) {
                Text(text = "도착 예정이 없습니다." ,style = mBody.copy(TextColor))
            }
            destinationTransitInfo.forEachIndexed { index, dest ->
                ArrivedText(
                    routeno = dest.routeno,
                    arrtime = dest.arrtime,
                    arrprevstationcnt = dest.arrprevstationcnt,
                    color1 = colorT2,
                    color2 = colorT1
                )
               if (index == 0)  WidthSpacer(8.dp)
            }

        }
        WhiteDivider(modifier = Modifier.align(Alignment.TopCenter), cornerRadius = 16.dp)
    }
}

@Composable
fun ArrivedText(
    routeno: String,
    arrtime: Int,
    arrprevstationcnt: String,
    color1: Color,
    color2: Color,
) {
    Text(text = buildAnnotatedString {
        append("$routeno ")
        withStyle(
            style = mBody.copy(color = color1).toSpanStyle()
        ) { append("(${arrtime.toFormatKrTime()}) ") }
        withStyle(
            style = mBody2.copy(color = color2).toSpanStyle()
        ) { append("${arrprevstationcnt}]") }
    }, style = mBody.copy(TextColor))
}

/**
 * 좌측 round와 우측 round를 가리기 위해 흰색 배경 설정
 */
@Composable
fun WhiteDivider(
    modifier: Modifier,
    strokeWidth: Dp = 3.dp,
    cornerRadius: Dp = 16.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(strokeWidth) // divider 높이
    ) {
        // 흰색으로 먼저 배경 칠해
        drawLine(
            color = TextWColor,
            start = Offset(cornerRadius.toPx(), size.height / 2),
            end = Offset(size.width - cornerRadius.toPx(), size.height / 2),
            strokeWidth = strokeWidth.toPx(),
        )
    }
}

/**
 * 점선 구분선
 */
@Composable
fun DashedDivider(
    modifier: Modifier,
    color: Color = Color.Red,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 10.dp,
    dashGap: Dp = 6.dp,
    cornerRadius: Dp = 16.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(strokeWidth) // divider 높이
    ) {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashWidth.toPx(), dashGap.toPx()), 0f
            )
        )

        // 왼쪽 라운드 끝 + 오른쪽 라운드 시작 전까지만 그리기
        drawLine(
            color = color,
            start = Offset(cornerRadius.toPx(), size.height / 2),
            end = Offset(size.width - cornerRadius.toPx(), size.height / 2),
            strokeWidth = strokeWidth.toPx(),
            pathEffect = stroke.pathEffect
        )
    }
}

/**
 * @param startPoint: 교통정보 도착 정보를 받아오는 변수 이름, 버스의 경우 버스 정류장: "더샾 센트리체", 지하철의 경우 지하철 이름: "잠실역"
 */
@Composable
fun RowScope.TicketTransitContent(
    contentColor: Color,
    step: String,
    isCurrentStep: Boolean,
    startPoint: String,
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
                .isCurrentStep(isCurrentStep = isCurrentStep, color = contentColor)
                .padding(horizontal = 9.5.dp, vertical = 2.dp)
        ) {
            Text(
                text = step,
                style = normal10sp,
                color = if (isCurrentStep) contentColor else contentColor,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        HeightSpacer(height = 4.dp)
        Text(text = startPoint, style = sbTitle4, color = TextColor, overflow = TextOverflow.Ellipsis)
    }
}

fun Modifier.isCurrentStep(isCurrentStep: Boolean, color: Color) =
    if (isCurrentStep) this.background(color)
    else this.border(width = 1.dp, color = color, shape = RoundedCornerShape(20.dp))


@Composable
@Preview(showBackground = true, backgroundColor = 0xffffffff)
fun ScheduleTicketPreview() {
    ScheduleTicket(
        scheduleName = "출근",
        transit = emptyList(),
        destinationName = "집"
    )
}