package com.busschedule.model

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

val BusStopInfoType = object : NavType<BusStopInfo>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): BusStopInfo? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): BusStopInfo {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: BusStopInfo) {
        bundle.putString(key, Json.encodeToString(BusStopInfo.serializer(), value))
    }

    override fun serializeAsValue(value: BusStopInfo): String {
        return Json.encodeToString(BusStopInfo.serializer(), value)
    }
}
