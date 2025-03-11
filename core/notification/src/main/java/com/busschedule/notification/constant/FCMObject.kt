package com.busschedule.notification.constant

import com.busschedule.model.BusStopInfo

class FCMObject(private val data: List<BusStopInfo>) {
    private var cur = 0

    fun next(): BusStopInfo {
        return data[++cur]
    }

    fun isPrevious(): Boolean {
        return cur > 0
    }

    fun previous(): BusStopInfo {
        return data[--cur]
    }
}