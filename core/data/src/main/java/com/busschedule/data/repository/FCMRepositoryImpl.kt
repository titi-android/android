package com.busschedule.data.repository

import com.busschedule.data.api.FCMApi
import com.busschedule.domain.repository.FCMRepository
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(private val fcmApi: FCMApi) : FCMRepository {
//    override fun postFCMToken(fcmToken: FCMTokenRequest): Flow<ApiResult<Unit>> = safeFlowUnit {
//        fcmApi.postFCMToken(fcmToken)
//    }
}