package com.busschedule.data.network

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleErrorResponse(
    val errorCode: String,
    val reason: String,
    val extra: Map<String, String>? = null,
)