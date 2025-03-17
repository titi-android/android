package com.busschedule.register.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class NotifyInfo (
    val icon: ImageVector,
    val containerColor: Color,
    val iconColor: Color,
    val content: String
)