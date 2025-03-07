package com.busschedule.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class InquiryRequest(
    val title: String,
    val content: String
)
