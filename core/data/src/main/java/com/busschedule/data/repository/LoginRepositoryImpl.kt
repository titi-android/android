package com.busschedule.data.repository

import com.busschedule.data.network.LoginApi
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.LoginUser
import com.busschedule.domain.model.safeFlow
import com.busschedule.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
): LoginRepository {
    override fun login(loginUser: LoginUser): Flow<ApiState<String>> = safeFlow { loginApi.login(loginUser) }
    override fun signup(loginUser: LoginUser): Flow<ApiState<Unit>> = safeFlow { loginApi.signup(loginUser) }
}