package com.busschedule.domain.usecase.fcm

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.FCMRepository
import javax.inject.Inject

class DeleteFCMTokenUseCase @Inject constructor(private val fcmRepository: FCMRepository) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        fcmRepository.deleteFCMToken()
    }

}