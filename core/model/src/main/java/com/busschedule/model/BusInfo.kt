package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class BusInfo(val name: String, val type: String)
