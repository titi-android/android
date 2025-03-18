package com.busschedule.domain.usecase.user

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.TokenRepository
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        tokenRepository.validateToken()
    }
}