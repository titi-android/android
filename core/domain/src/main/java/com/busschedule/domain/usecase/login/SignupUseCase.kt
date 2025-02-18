package com.busschedule.domain.usecase.login

import com.busschedule.domain.model.LoginUser
import com.busschedule.domain.repository.LoginRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val loginRepository: LoginRepository){
//    operator fun invoke(loginUser: LoginUser) = loginRepository.signup(loginUser)
    operator fun invoke(loginUser: LoginUser) {}
}