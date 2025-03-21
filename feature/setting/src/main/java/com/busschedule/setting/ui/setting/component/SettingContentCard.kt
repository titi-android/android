package com.busschedule.setting.ui.setting.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcForwardArrow
import core.designsystem.theme.Primary

@Composable
fun SettingContentCard(
    icon: ImageVector,
    onClick: () -> Unit = {},
    hideForwardIcon: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) {
    WhiteRoundedCard(
        padding = PaddingValues(horizontal = 16.dp),
        onClick = { onClick() }) {
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
        if (hideForwardIcon) {
            Icon(
                imageVector = MyIconPack.IcForwardArrow,
                contentDescription = "ic_forward",
                modifier = Modifier.size(24.dp),
                tint = Primary
            )
        }
    }
}
