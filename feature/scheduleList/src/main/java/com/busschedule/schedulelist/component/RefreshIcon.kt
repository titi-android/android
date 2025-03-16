package com.busschedule.schedulelist.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcRefresh
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor

@Composable
fun BoxScope.RefreshIcon(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .align(Alignment.BottomEnd),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Primary,
            contentColor = TextWColor
        ), onClick = { onClick() }) {
        Icon(
            imageVector = MyIconPack.IcRefresh,
            contentDescription = "ic_refresh",
            modifier = Modifier.size(24.dp)
        )
    }
}