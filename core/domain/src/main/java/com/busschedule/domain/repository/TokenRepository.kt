package com.busschedule.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun saveAccessToken(token: String)

    suspend fun saveFCMToken(token: String)

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>

    suspend fun deleteAccessToken()

    suspend fun validateToken(): String
}