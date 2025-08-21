package com.busschedule.util.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.applyBlur(isBlur: Boolean): Modifier =
    if (isBlur)blur(
        radiusX = 4.dp,
        radiusY = 4.dp,
    ) else this

fun Modifier.customNavigationBarPadding(state: Boolean) =
    if (state) this.navigationBarsPadding() else this