package com.busschedule.util.ext

// second --> minute
fun Int.toFormatTime() = (this % 3_600) / 60
