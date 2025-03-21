package com.busschedule.schedulelist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.busschedule.common.R
import com.busschedule.util.ext.noRippleClickable
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcMainlogo
import core.designsystem.svg.myiconpack.IcSetting
import core.designsystem.theme.Primary
import core.designsystem.theme.logo

@Composable
fun ScheduleListAppBar(onClickSetting: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = MyIconPack.IcMainlogo,
                contentDescription = "ic_mainlogo",
                modifier = Modifier.width(32.dp)
            )
            WidthSpacer(width = 6.dp)
            Text(text = stringResource(id = R.string.app_name), style = logo)
        }
        Icon(imageVector = MyIconPack.IcSetting,
            contentDescription = "ic_setting",
            tint = Primary,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable { onClickSetting() })
    }
}