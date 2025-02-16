package com.busschedule.schedulelist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcMainlogo
import core.designsystem.svg.myiconpack.IcSetting
import core.designsystem.theme.Primary
import core.designsystem.theme.logo

@Composable
fun ScheduleListAppBar(onClickSetting: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = MyIconPack.IcMainlogo,
                contentDescription = "ic_mainlogo",
                modifier = Modifier.width(32.dp)
            )
            WidthSpacer(width = 6.dp)
            Text(text = "떠나링", style = logo)
        }
        Icon(imageVector = MyIconPack.IcSetting,
            contentDescription = "ic_setting",
            tint = Primary,
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickSetting() })
    }
}