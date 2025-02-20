package com.busschedule.domain.usecase.user

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.UserRepository
import javax.inject.Inject

class PostInquiryUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(title: String, content: String) = runCatchingIgnoreCancelled {
        userRepository.postInquiry(title, content)
    }
}