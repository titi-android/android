package com.busschedule.model

import kotlinx.serialization.Serializable

@Serializable
data class Subway(
    val regionName: String,
    val lineName: String,
    val stationName: String,
    val dir: String,
    val arrivals: List<SubwayArrival>
)

@Serializable
data class SubwayArrival(
    val subwayId: String,
    val updnLine: String,
    val statnNm: String,
    val barvlDt: String,
    val arvlMsg2: String,
    val arvlCd: String
)