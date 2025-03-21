package com.busschedule.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun saveAccessToken(token: String)

    suspend fun saveFCMToken(token: String)

    suspend fun saveAutoLoginState(state: Boolean)

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    fun getAutoLoginState(): Flow<Boolean>

    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()

    suspend fun validateToken(): String
}