package com.busschedule.data.data.repository

import com.busschedule.data.local.datastore.TokenManager
import com.busschedule.data.remote.api.UserApi
import com.busschedule.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val tokenManager: TokenManager, private val userApi: UserApi):
    TokenRepository {
    override suspend fun saveAccessToken(token: String) {
        tokenManager.saveAccessToken(token)
    }

    override suspend fun saveFCMToken(token: String) {
        tokenManager.saveFCMToken(token)
    }

    override fun getAccessToken(): Flow<String?> =
        tokenManager.getAccessToken()

    override fun getRefreshToken(): Flow<String?> =
        tokenManager.getRefreshToken()

    override suspend fun deleteAccessToken() {
        tokenManager.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken() {
        tokenManager.deleteRefreshToken()
    }

    override suspend fun validateToken(): String {
        return try {
            val accessToken = userApi.validateToken().getOrThrow().data?.accessToken
            accessToken?.let { tokenManager.saveAccessToken(it) }
            accessToken!!
        } catch (e: NullPointerException) {
            return ""
        }
    }
}