package com.busschedule.domain.usecase.user

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled { userRepository.delete() }
}