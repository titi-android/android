package com.busschedule.domain.model.response.busstop

import kotlinx.serialization.Serializable

@Serializable
data class BusResponse(val name: String, val type: String)
