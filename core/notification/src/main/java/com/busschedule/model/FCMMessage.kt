package com.busschedule.model

import com.busschedule.util.ext.toFormatKrTime

data class FCMMessage(
    val scheduleId: String,
    val scheduleName: String,
    val busStopInfos: BusStopInfo,
) {
    fun getTitle() = "$scheduleName | ${busStopInfos.busStopName}"

    private fun formatArrPrevStationCnt(cnt: Int): String {
        if (cnt <= 1) {
            return "(곧 도착)"
        }
        return "($cnt 정거장)"
    }

    fun getContent(): String {
        return busStopInfos.busInfos.joinToString(separator = ", ") {
            "${it.routeno}번 ${it.arrtime.toFormatKrTime()} ${formatArrPrevStationCnt(it.arrprevstationcnt)}"
        }
    }
}