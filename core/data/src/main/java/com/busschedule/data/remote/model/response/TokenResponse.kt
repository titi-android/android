package com.busschedule.data.remote.model.response

import com.busschedule.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String? = null
)


fun TokenResponse.asDomain() = Token(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken ?: ""
)