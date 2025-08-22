package com.busschedule.data.local.room

import androidx.room.TypeConverter
import com.busschedule.model.BusInfo
import com.busschedule.model.BusStop
import com.busschedule.model.BusStopInfo
import com.busschedule.model.BusRegister
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

    @TypeConverter
    fun fromStringListInfoToJson(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJsonToStringList(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromRouteInfoToJson(value: List<BusRegister>): String {
        val gson = Gson()
        val type = object : TypeToken<List<BusRegister>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJsonToRouteInfo(value: String): List<BusRegister> {
        val gson = Gson()
        val type = object : TypeToken<List<BusRegister>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBusStopToJson(value: BusStop): String {
        val gson = Gson()
        val type = object : TypeToken<BusStop>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJsonToBusStop(value: String): BusStop {
        val gson = Gson()
        val type = object : TypeToken<BusStop>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromBusInfoToJson(value: BusInfo): String {
        val gson = Gson()
        val type = object : TypeToken<BusInfo>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun fromJsonToBusInfo(value: String): BusInfo {
        val gson = Gson()
        val type = object : TypeToken<BusInfo>() {}.type
        return gson.fromJson(value, type)
    }
}