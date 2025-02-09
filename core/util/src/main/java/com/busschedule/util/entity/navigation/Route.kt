package com.busschedule.util.entity.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data class RegisterSchedule(
        val id: Int? = null,
    ): Route
}