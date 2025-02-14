package com.busschedule.navigation

import com.busschedule.model.BusStopInfo
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object ScheduleList: Route

    @Serializable
    data object LoginGraph: Route {
        @Serializable
        data object Login: Route
        @Serializable
        data object Signup: Route
    }

    @Serializable
    data object RegisterGraph: Route {
        @Serializable
        data class RegisterSchedule(
            val id: Int? = null,
        ): Route
        @Serializable
        data object SelectRegion : Route
        @Serializable
        data class SelectBusStop(
            val busStopInfo: BusStopInfo = BusStopInfo()
        ): Route
    }

    @Serializable
    data object SettingGraph: Route {
        @Serializable
        data object Setting: Route
        @Serializable
        data object Ask : Route
        @Serializable
        data object EditProfile: Route
    }
}


