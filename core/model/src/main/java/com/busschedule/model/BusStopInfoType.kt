package com.busschedule.model

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

val BusStopType = object : NavType<BusStop>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): BusStop? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): BusStop {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: BusStop) {
        bundle.putString(key, Json.encodeToString(BusStop.serializer(), value))
    }

    override fun serializeAsValue(value: BusStop): String {
        return Json.encodeToString(BusStop.serializer(), value)
    }
}
