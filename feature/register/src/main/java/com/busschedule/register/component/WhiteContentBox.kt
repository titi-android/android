package com.busschedule.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcSearch
import core.designsystem.theme.TextMColor
import core.designsystem.theme.rTextBox

@Composable
fun WhiteContentBox(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(start = 16.dp, end = 18.5.dp, top = 14.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = rTextBox.copy(TextMColor))
        Icon(
            imageVector = MyIconPack.IcSearch,
            contentDescription = "ic_search",
            tint = TextMColor,
            modifier = Modifier.size(24.dp)
        )
    }
}