package com.busschedule.subway.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
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
import core.designsystem.component.WidthSpacer
import core.designsystem.shadow.titiShadow
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextBox

@Composable
@Preview
fun BoxScope.SelectStationBox() {
    val color = Color(0xFF2E4291)
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TextWColor,
            contentColor = Color.Black,
        ),
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .titiShadow(
                color = Color.Black.copy(alpha = 0.18f),
                blurRadius = 4.dp,
                borderRadius = 12.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SelectStationPrefixText(name = "출발", color = color)
                WidthSpacer(16.dp)
                Text("종각", style = rTextBox)
            }
            WidthSpacer(32.dp)
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            WidthSpacer(32.dp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                SelectStationPrefixText(name = "도착", color = color)
                WidthSpacer(16.dp)
                Text("서울", style = rTextBox)
            }

        }
        WidthSpacer(16.dp)
    }
}