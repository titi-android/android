package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class TransitInfo(
    val type: String,
    val busStop: BusStopInfo?,
    val subway: Subway?,
    val orderIndex: Int
) {
    companion object {
        val EMPTY =
            TransitInfo(
                type = "",
                busStop = null,
                subway = null,
                orderIndex = 0
            )

    }
}
