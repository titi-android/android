package com.busschedule.domain.repository

import com.busschedule.model.Token

interface LoginRepository {
    suspend fun login(name: String, password: String): Token
//    fun signup(loginUser: LoginUser): Flow<com.busschedule.data.network.ApiState<Unit>>
}