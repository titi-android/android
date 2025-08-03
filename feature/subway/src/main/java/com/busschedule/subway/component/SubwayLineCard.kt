package com.busschedule.subway.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextWColor
import core.designsystem.theme.sbTitle3

@Composable
fun SubwayLineCard(name: String, isSelected: Boolean, onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E4291),
            contentColor = TextWColor,
            disabledContainerColor = TextBoxDis,
            disabledContentColor = TextWColor
        ),
        enabled = isSelected,
        onClick = onClick
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Text(text = "${name}호선", style = sbTitle3.copy(color = TextWColor))
        }
    }
}