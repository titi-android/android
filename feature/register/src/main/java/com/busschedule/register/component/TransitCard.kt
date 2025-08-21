package com.busschedule.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.busschedule.model.BusInfo
import com.busschedule.register.model.TransitCardUI
import com.busschedule.register.model.TransitPointType
import com.busschedule.util.ext.applyBlur
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainButton
import core.designsystem.component.button.MainOutlineButton
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcBus
import core.designsystem.svg.myiconpack.IcForwardArrow
import core.designsystem.theme.Primary
import core.designsystem.theme.Primary3
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mBody
import core.designsystem.theme.mButton
import core.designsystem.theme.sbTitle2
import core.designsystem.theme.sbTitle3


@Composable
@Preview
fun TransitCard(
    type: TransitPointType = TransitPointType.START,
    id: Int = 0,
    transitCardUI: TransitCardUI = TransitCardUI.Bus(),
    onInitClick: (Boolean) -> Unit = {},
    onEditClick: (Boolean) -> Unit = {},
    onRemoveClick: (Int) -> Unit = {},
) {
    val isNotInit = transitCardUI.isEmpty()

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TextWColor,
            contentColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .applyBlur(isNotInit)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = type.value, style = sbTitle2.copy(fontSize = 22.sp))
                        WidthSpacer(2.dp)
                        Image(
                            imageVector = transitCardUI.icon,
                            contentDescription = "image_transit",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Row {
                        MainOutlineButton(text = "수정") { onEditClick(isNotInit) }
                        WidthSpacer(8.dp)
                        if (TransitPointType.isTransfer(type)) {
                            MainButton(text = "삭제") { onRemoveClick(id) }
                        }
                    }
                }
                HeightSpacer(16.dp)
                TransitCardContentRow(title = transitCardUI.title1, content = transitCardUI.content1)
                HeightSpacer(16.dp)
                TransitCardContentRow(title = transitCardUI.title2, content = transitCardUI.content2)
                HeightSpacer(16.dp)
                when(transitCardUI) {
                    is TransitCardUI.Bus -> {
                        TransitCardBusNumContentRow(buses = transitCardUI.buses)
                    }
                    is TransitCardUI.Subway -> {
                        TransitCardContentRow(title = transitCardUI.title3, content = transitCardUI.subwayDirection)
                    }
                }
            }
            if (isNotInit) {
                StartInitButton(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${type.value}지 입력하기"
                ) { onInitClick(true) }
            }
        }

    }
}

@Composable
fun TransitCardContentRow(title: String, content: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = sbTitle3.copy(TextBoxDis),
            modifier = Modifier.weight(0.3f)
        )
        Text(text = content, style = mBody.copy(Primary), modifier = Modifier.weight(0.7f))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransitCardBusNumContentRow(title: String = "버스 번호", buses: List<BusInfo>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = sbTitle3.copy(TextBoxDis),
            modifier = Modifier.weight(0.3f)
        )
        FlowRow(
            modifier = Modifier.weight(0.7f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buses.forEach { bus ->
                BusBox(
                    icon = MyIconPack.IcBus,
                    name = bus.name,
                    type = bus.type,
                ) { }
            }
        }
    }
}

@Composable
fun StartInitButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.White,
            contentColor = TextColor,
        ),
        border = BorderStroke(width = 1.dp, color = TextBoxDis),
        onClick = { onClick() }) {
        Text(text = text, style = mButton)
        WidthSpacer(4.dp)
        Icon(
            imageVector = MyIconPack.IcForwardArrow,
            contentDescription = "ic_forward",
            modifier = Modifier.size(24.dp),
            tint = Primary3
        )
    }
}