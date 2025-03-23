package com.busschedule.setting.ui.setting.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.theme.BusGreen2
import core.designsystem.theme.Primary
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor

@Composable
fun PushNotifyCheckingCard(
    icon: ImageVector,
    isCheck: Boolean = true,
    onClickOnCheck: () -> Unit = {},    // 알림 켜진 상태에서 호출되는 함수
    onClickOffCheck: () -> Unit = {},   // 알림 꺼진 상태에서 호출되는 함수
    content: @Composable RowScope.() -> Unit,
) {

    val onClickFromCheck = if(isCheck) { onClickOnCheck } else { onClickOffCheck }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = TextWColor, contentColor = TextMColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 11.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = icon,
                    contentDescription = "ic_forward",
                    modifier = Modifier.size(24.dp),
                    tint = Primary
                )
                WidthSpacer(width = 16.dp)
                content()
            }
            Switch(
                checked = isCheck,
                onCheckedChange = { onClickFromCheck() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = TextWColor,
                    checkedTrackColor = BusGreen2,
                    uncheckedThumbColor = Primary,
                    uncheckedTrackColor = TextBoxDis,
                    uncheckedBorderColor = TextBoxDis
                )
            )
        }
    }
}

@Preview
@Composable
fun CheckBoxPreview(modifier: Modifier = Modifier) {
    PushNotifyCheckingCard(icon = MyIconPack.IcNotify, onClickOnCheck = {}, content = {})
}
