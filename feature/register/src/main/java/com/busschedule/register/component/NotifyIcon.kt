package com.busschedule.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.busschedule.register.model.NotifyInfo
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcOffnotify
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextBox

@Composable
fun NotifyIcon(isCheck: Boolean = false, onCheck: (Boolean) -> Unit) {
    val notifyInfo = if (isCheck) {
        NotifyInfo(
            icon = MyIconPack.IcNotify,
            containerColor = Primary,
            iconColor = TextWColor,
            content = "알림 ON"
        )
    } else {
        NotifyInfo(
            icon = MyIconPack.IcOffnotify,
            containerColor = TextWColor,
            iconColor = Primary,
            content = "알림 OFF"
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .size(44.dp)
            .background(notifyInfo.containerColor)
            .clickable { onCheck(!isCheck) }) {
            Icon(
                imageVector = notifyInfo.icon,
                contentDescription = "ic_notify",
                tint = notifyInfo.iconColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.6f)
            )
        }
        WidthSpacer(width = 8.dp)
        Text(text = notifyInfo.content, style = rTextBox)
    }
}