package com.busschedule.data

import com.busschedule.data.network.ScheduleErrorResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Json {
    val instance = Json { encodeDefaults = true }

    fun getScheduleErrorBody(body: String) = instance.decodeFromString<ScheduleErrorResponse>(body)
}