package com.busschedule.data.repository

import com.busschedule.data.network.FCMApi
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.FCMTokenRequest
import com.busschedule.domain.model.safeFlowUnit
import com.busschedule.domain.repository.FCMRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(private val fcmApi: FCMApi) : FCMRepository {
    override fun postFCMToken(fcmToken: FCMTokenRequest): Flow<ApiState<Unit>> = safeFlowUnit {
        fcmApi.postFCMToken(fcmToken)
    }
}