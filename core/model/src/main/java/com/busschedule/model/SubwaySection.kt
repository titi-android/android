package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class SubwaySection(
    val regionName: String = "", // 서울
    val lineName: String = "", // 1호선
    val stationName: String = "",  // 잠실
    val dir: String = "", // UP, DOWN
)
