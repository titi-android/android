package com.busschedule.domain.usecase.login

import com.busschedule.common.runCatchingIgnoreCancelled
import com.busschedule.domain.repository.LoginRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    //    operator fun invoke(loginUser: LoginUser) = loginRepository.signup(loginUser)
    suspend operator fun invoke(name: String, password: String) = runCatchingIgnoreCancelled {
        loginRepository.signup(name, password)
    }
}