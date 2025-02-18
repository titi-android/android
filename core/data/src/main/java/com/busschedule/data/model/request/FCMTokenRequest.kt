package com.busschedule.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FCMTokenRequest(val token: String)
