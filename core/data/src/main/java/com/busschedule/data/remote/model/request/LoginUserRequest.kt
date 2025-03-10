package com.busschedule.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserRequest(
    val name: String,
    val password: String
)
