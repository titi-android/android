package com.busschedule.subway.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.shadow.titiShadow
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcForwardArrow
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextBox

@Composable
@Preview
fun SelectStationBox(
    modifier: Modifier = Modifier,
    isVisibility: Boolean = false,
    startStation: String = "서울역",
    endStation: String = "종각",
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibility,
        enter = slideInHorizontally(initialOffsetX = {it}),
        exit = slideOutHorizontally(targetOffsetX = {it})
    ) {
        val color = Color(0xFF2E4291)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = TextWColor,
                contentColor = Color.Black,
            ),
            modifier = modifier
                .titiShadow(
                    color = Color.Black.copy(alpha = 0.18f),
                    blurRadius = 4.dp,
                    borderRadius = 12.dp
                )
        ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {

            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SelectStationPrefixText(modifier = Modifier.weight(1f), name = "출발", color = color)
                    WidthSpacer(16.dp)
                    Text(
                        text = startStation,
                        style = rTextBox,
                        modifier = Modifier.weight(1f),
                    )
                }
                HeightSpacer(12.dp)
                Icon(
                    imageVector = MyIconPack.IcForwardArrow,
                    contentDescription = "",
                    modifier = Modifier.size(16.dp)
                )
                HeightSpacer(12.dp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SelectStationPrefixText(modifier = Modifier.weight(1f), name = "도착", color = color)
                    WidthSpacer(16.dp)
                    Text(endStation, style = rTextBox, modifier = Modifier.weight(1f),)
                }
            }
        }
    }

}