package com.busschedule.data.model.response

import com.busschedule.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)


fun TokenResponse.asDomain() = Token(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)