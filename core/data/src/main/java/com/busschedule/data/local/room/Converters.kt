package com.busschedule.data.local.room

import androidx.room.TypeConverter
import com.busschedule.model.BusStopInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromBusStopInfoToJson(value: List<BusStopInfo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<BusStopInfo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJsonToBusStopInfo(value: String): List<BusStopInfo> {
        val gson = Gson()
        val type = object : TypeToken<List<BusStopInfo>>() {}.type
        return gson.fromJson(value, type)
    }
}