package com.busschedule.data.repository

import com.busschedule.data.remote.api.FCMApi
import com.busschedule.data.remote.model.request.FCMTokenRequest
import com.busschedule.domain.repository.FCMRepository
import javax.inject.Inject

class FCMRepositoryImpl @Inject constructor(private val fcmApi: FCMApi) : FCMRepository {
    override suspend fun postFCMToken(token: String) {
        val fcmTokenRequest = FCMTokenRequest(token)
        fcmApi.postFCMToken(fcmTokenRequest).getOrThrow()
    }

}