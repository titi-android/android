package com.busschedule.data.repository

import com.busschedule.data.network.LoginApi
import com.busschedule.datastore.TokenManager
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.LoginUser
import com.busschedule.domain.model.Token
import com.busschedule.domain.model.safeFlowAndSaveToken
import com.busschedule.domain.model.safeFlowUnit
import com.busschedule.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val tokenManager: TokenManager,
) : LoginRepository {
    override fun login(loginUser: LoginUser): Flow<ApiState<Token>> =
        safeFlowAndSaveToken(apiFunc = { loginApi.login(loginUser) }) { accessToken ->
            tokenManager.saveAccessToken(accessToken)
        }

    override fun signup(loginUser: LoginUser): Flow<ApiState<Unit>> =
        safeFlowUnit { loginApi.signup(loginUser) }
}