package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.FCMTokenRequest
import kotlinx.coroutines.flow.Flow

interface FCMRepository {
    fun postFCMToken(fcmToken: FCMTokenRequest): Flow<ApiState<Unit>>
}