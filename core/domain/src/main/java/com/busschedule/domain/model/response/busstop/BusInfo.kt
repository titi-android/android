package com.busschedule.domain.model.response.busstop

import kotlinx.serialization.Serializable

@Serializable
data class BusInfo(val name: String, val type: String)
