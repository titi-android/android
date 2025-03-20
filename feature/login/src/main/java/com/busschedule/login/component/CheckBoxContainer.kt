package com.busschedule.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.busschedule.util.ext.noRippleClickable
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcOffCheckbox
import core.designsystem.svg.myiconpack.IcOnCheckbox
import core.designsystem.theme.Primary
import core.designsystem.theme.mTitle

@Composable
fun CheckBoxContainer(isCheck: Boolean, onCheckedChange: (Boolean) -> Unit, text: String) {
    val icon = if (isCheck.not()) MyIconPack.IcOffCheckbox else MyIconPack.IcOnCheckbox
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Image(
            imageVector = icon,
            contentDescription = "ic_checkbox",
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable { onCheckedChange(!isCheck) }
        )
        WidthSpacer(width = 16.dp)
        Text(
            text = text,
            style = mTitle.copy(Primary),
            modifier = Modifier.weight(1f)
        )
    }
}