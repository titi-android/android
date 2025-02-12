package com.busschedule.register.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.svg.IconPack
import core.designsystem.svg.myiconpack.IcOffCheckbox
import core.designsystem.svg.myiconpack.IcOnCheckbox

@Composable
fun CheckBoxIcon(isCheck: Boolean) {
    if (!isCheck) Image(
        imageVector = IconPack.IcOffCheckbox,
        contentDescription = "ic_off_checkbox",
        modifier = Modifier.size(24.dp),
    ) else {
        Image(
            imageVector = IconPack.IcOnCheckbox,
            contentDescription = "ic_on_checkbox",
            modifier = Modifier.size(24.dp),
        )
    }
}