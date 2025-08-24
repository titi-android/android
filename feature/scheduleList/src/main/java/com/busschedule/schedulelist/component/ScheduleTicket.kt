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
import com.busschedule.model.TransitInfo
import com.busschedule.util.ext.noRippleClickable
import com.busschedule.util.ext.toFormatKrTime
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcForwardArrow2
import core.designsystem.theme.BusGreen
import core.designsystem.theme.BusRed
import core.designsystem.theme.BusRedM1
import core.designsystem.theme.BusRedM2
import core.designsystem.theme.BusYellow
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
    mainColor: Color = BusRed,
    transit: List<TransitInfo>,
) {

    val rounded = 16.dp
    Column(modifier = Modifier.fillMaxWidth()) {
        // 상단 내용, 출근
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = rounded, topEnd = rounded))
                .drawBehind { drawRoundRect(color = mainColor) }
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = scheduleName, style = rTextBtn2.copy(color = TextWColor))
        }
        // 중간 내용
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
                        color = mainColor,
                        width = 1.dp,
                        shape = bottomRoundCornerShape
                    )
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                transit.forEachIndexed { index, busStopInfo ->
                    TicketTransitContent(
                        contentColor = BusGreen,
                        step = if (index == 0) "출발" else "환승",
                        busStop = "잠실",
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
                        tint = mainColor
                    )
                }
                TicketTransitContent(
                    contentColor = TextBoxDis,
                    step = "도착",
                    busStop = "집",
                    isCurrentStep = false
                ) {}
            }
            // 흰색 배경 먼저 칠해
            WhiteDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = BusYellow,
                cornerRadius = 16.dp
            )
            // 점선 구분선
            DashedDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = mainColor,
                cornerRadius = 16.dp
            )
        }
        // 하단 내용
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
                        color = mainColor,
                        width = 1.dp,
                        shape = fullRoundCornerShape
                    )
                    .background(Color.White, shape = fullRoundCornerShape)
//                .drawBehind { drawLine(color = BusIcBlue, start = Offset.Zero, end = size.center) }
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ArrivedText(
                    routeno = "306",
                    arrtime = 300,
                    arrprevstationcnt = 2
                )
                WidthSpacer(8.dp)
                ArrivedText(
                    routeno = "306",
                    arrtime = 300,
                    arrprevstationcnt = 2
                )

            }
            // 흰색 배경 먼저 칠해
            WhiteDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                color = TextWColor,
                cornerRadius = 16.dp
            )
        }
    }
}

@Composable
fun ArrivedText(routeno: String, arrtime: Int, arrprevstationcnt: Int) {
    Text(text = buildAnnotatedString {
        append("$routeno ")
        withStyle(style = mBody.copy(color = BusRedM1).toSpanStyle()) { append("(${arrtime.toFormatKrTime()}) ") }
        withStyle(style = mBody2.copy(color = BusRedM2).toSpanStyle()) { append("${arrprevstationcnt}정거장") }
    }, style = mBody.copy(TextColor))
}

@Composable
fun WhiteDivider(
    modifier: Modifier,
    color: Color = Color.Red,
    strokeWidth: Dp = 3.dp,
    dashWidth: Dp = 10.dp,
    dashGap: Dp = 6.dp,
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

@Composable
fun RowScope.TicketTransitContent(
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
        Text(text = busStop, style = sbTitle4, color = TextColor, overflow = TextOverflow.Ellipsis)
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
        mainColor = BusRed,
        transit = listOf(TransitInfo.EMPTY, TransitInfo.EMPTY)
    )
}