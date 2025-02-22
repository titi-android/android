package com.busschedule.util.ext

// second --> minute
fun Int.toFormatKrTime(): String {
    return when {
        this / 3_600 >= 1 -> "${this / 3_600}시간"
        this / 60 >= 1 -> "${this / 60}분"
        else -> "${this}초"
    }
}

fun Int.toFormatEnTime(): String {
    return when {
        this / 3_600 >= 1 -> "${this / 3_600}h"
        this / 60 >= 1 -> "${this / 60}m"
        else -> "${this}s"
    }
}