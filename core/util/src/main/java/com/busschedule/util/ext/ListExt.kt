package com.busschedule.util.ext

// hour, minute 데이터 리스트
fun List<Int>.toFormatKrTime(): String {
    val hour = this[0]
    val minute = if (this[1] == 0) "00" else "${this[1]}"
    return "${hour}:$minute"
}