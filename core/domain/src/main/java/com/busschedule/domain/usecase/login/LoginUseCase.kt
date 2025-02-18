package com.busschedule.domain.usecase.login

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository){
    suspend operator fun invoke(name: String, password: String) = runCatchingIgnoreCancelled {
        loginRepository.login(name, password)
    }
}
