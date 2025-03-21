package com.busschedule.setting.ui.setting.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor

@Composable
fun WhiteRoundedCard(
    padding: PaddingValues,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        colors = CardDefaults.cardColors(containerColor = TextWColor, contentColor = TextMColor),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}