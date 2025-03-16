package com.busschedule.schedulelist.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcRefresh
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor

@Composable
fun BoxScope.RefreshIcon(modifier: Modifier, isLoading: Boolean, onClick: () -> Unit) {
    val rotate = remember { Animatable(0f) }
    LaunchedEffect(isLoading) {
        if (isLoading) {
            rotate.animateTo(
                targetValue = rotate.value - 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            return@LaunchedEffect
        }
        rotate.animateTo(targetValue = 0f)
    }
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
            modifier = Modifier
                .size(24.dp)
                .rotate(rotate.value)
        )
    }
}