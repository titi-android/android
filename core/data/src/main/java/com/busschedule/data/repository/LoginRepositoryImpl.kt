package com.busschedule.data.repository

import com.busschedule.data.api.UserApi
import com.busschedule.data.model.request.LoginUserRequest
import com.busschedule.data.model.response.asDomain
import com.busschedule.datastore.TokenManager
import com.busschedule.domain.repository.LoginRepository
import com.busschedule.model.Token
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
) : LoginRepository {
    override suspend fun login(name: String, password: String): Token {
        val user = LoginUserRequest(name = name, password = password)
        return userApi.login(user).getOrThrow().data!!.asDomain().also {
            tokenManager.saveAccessToken(it.accessToken)
        }
    }
    override suspend fun signup(name: String, password: String) {
        val user = LoginUserRequest(name = name, password = password)
        userApi.signup(user).getOrThrow().data
    }
}