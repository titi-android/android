package com.busschedule.util.entity.navigation

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
        data object SelectBusStop: Route
    }
}


