package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class NotifyMessage(
    val title: String,
    val detail: String,
)
