package com.busschedule.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse<D>(
    val data : D? = null,
    val message: String = "",
    val success: Boolean = false,
)