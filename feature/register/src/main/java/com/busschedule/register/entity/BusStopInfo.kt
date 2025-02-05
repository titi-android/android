package com.busschedule.register.entity


data class BusStopInfo (val id: String, val name: String, val buses: List<Bus>)

@JvmInline
value class Bus(val name: String)

