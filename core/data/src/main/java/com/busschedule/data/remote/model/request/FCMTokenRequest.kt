package com.busschedule.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FCMTokenRequest(val token: String)
