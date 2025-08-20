package com.busschedule.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val x: String = "",
    val y: String = ""
)
