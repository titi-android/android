package com.busschedule.domain.usecase.fcm

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.FCMRepository
import javax.inject.Inject

class PostFCMTokenUseCase @Inject constructor(private val fcmRepository: FCMRepository) {
    suspend operator fun invoke(token: String) = runCatchingIgnoreCancelled { fcmRepository.postFCMToken(token) }
}