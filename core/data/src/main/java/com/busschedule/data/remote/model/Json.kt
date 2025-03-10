package com.busschedule.data.remote.model

import kotlinx.serialization.json.Json

object Json {
    val instance = Json { encodeDefaults = true }

    fun getScheduleErrorBody(body: String) = instance.decodeFromString<ScheduleErrorResponse>(body)
}