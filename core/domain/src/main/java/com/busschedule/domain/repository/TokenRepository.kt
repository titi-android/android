package com.busschedule.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TokenRepository {
    suspend fun saveAccessToken(token: String)

    suspend fun saveFCMToken(token: String)

    fun getAccessToken(): Flow<String?>

    fun getRefreshToken(): Flow<String?>
}