package com.busschedule.model

data class FCMMessage(
    val scheduleName: String,
    val days: String,
    val busStopName: String,
    val firstBusName: String,
    val firstArrPrevStCnt: String,
    val firstArrTime: String,
    val secondBusName: String,
    val secondArrPrevStCnt: String,     // 잔여 버스 정류장 수
    val secondArrTime: String,
) {
    fun getTitle() = "$scheduleName $busStopName"

    fun getContent() =
        "${firstBusName}번 ${firstArrTime}분 (${firstArrPrevStCnt}정거장), ${secondBusName}번 ${secondArrTime}분 (${secondArrPrevStCnt}정거장)"
}