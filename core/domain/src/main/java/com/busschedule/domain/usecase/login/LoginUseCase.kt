package com.busschedule.domain.usecase.login

import com.busschedule.domain.model.LoginUser
import com.busschedule.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository){
    operator fun invoke(loginUser: LoginUser) =
        loginRepository.login(loginUser)
}