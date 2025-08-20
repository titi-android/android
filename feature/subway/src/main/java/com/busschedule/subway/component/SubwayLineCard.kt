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
import com.busschedule.model.TextBoxColor
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextWColor
import core.designsystem.theme.sbTitle3

@Composable
fun SubwayLineCard(name: String, isSelected: Boolean, onClick: () -> Unit) {

    val colors = if (isSelected) {
        TextBoxColor(container = Color(0xFF2E4291), content = TextWColor)
    } else {
        TextBoxColor(container = TextBoxDis, content = TextWColor)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.container,
            contentColor = colors.content
        ),
        onClick = onClick
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Text(text = name, style = sbTitle3.copy(color = TextWColor))
        }
    }
}