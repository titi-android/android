package com.busschedule.navigation

import com.busschedule.model.BusStop
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object ScheduleList: Route

    @Serializable
    data object RegisterGraph: Route {
        @Serializable
        data class RegisterSchedule(
            val id: Int? = null,
            val isExistTempSchedule: Boolean = false,
            val dayOfWeek: String = "월요일"
        ): Route
        @Serializable
        data class SelectRegion(val id: Int = 0) : Route
        @Serializable
        data class SelectBusStop(val busStop: BusStop): Route
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

@Serializable
sealed interface LoginGraph: Route {
    @Serializable
    data object Splash: LoginGraph
    @Serializable
    data object Start: LoginGraph
    @Serializable
    data object Login: LoginGraph
    @Serializable
    data object Signup: LoginGraph
}