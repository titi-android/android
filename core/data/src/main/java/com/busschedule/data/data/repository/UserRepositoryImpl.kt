package com.busschedule.data.data.repository

import com.busschedule.data.local.datastore.TokenManager
import com.busschedule.data.remote.api.LoginApi
import com.busschedule.data.remote.api.UserApi
import com.busschedule.data.remote.model.request.InquiryRequest
import com.busschedule.data.remote.model.request.LoginUserRequest
import com.busschedule.data.remote.model.response.asDomain
import com.busschedule.domain.repository.UserRepository
import com.busschedule.model.Token
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
) : UserRepository {
    override suspend fun login(name: String, password: String): Token {
        val user = LoginUserRequest(name = name, password = password)
        return loginApi.login(user).getOrThrow().data!!.asDomain().also {
            tokenManager.saveAccessToken(it.accessToken)
            tokenManager.saveRefreshToken(it.refreshToken)
        }
    }
    override suspend fun signup(name: String, password: String) {
        val user = LoginUserRequest(name = name, password = password)
        loginApi.signup(user).getOrThrow().data
    }

    override suspend fun delete() { userApi.delete() }

    override suspend fun postInquiry(title: String, content: String) {
        val inquiry = InquiryRequest(title = title, content = content)
        userApi.postInquiry(inquiry)
    }
}