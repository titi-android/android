package com.busschedule.domain.usecase.fcm

import com.busschedule.domain.repository.FCMRepository
import javax.inject.Inject

class PostFCMTokenUseCase @Inject constructor(private val fcmRepository: FCMRepository) {
    //    operator fun invoke(fcmTokenRequest: FCMTokenRequest) = fcmRepository.postFCMToken(fcmTokenRequest)
    suspend operator fun invoke(token: String) = fcmRepository.postFCMToken(token)

}