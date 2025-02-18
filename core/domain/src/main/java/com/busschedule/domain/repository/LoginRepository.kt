package com.busschedule.domain.repository

import com.busschedule.model.Token

interface LoginRepository {
    suspend fun login(name: String, password: String): Token
    suspend fun signup(name: String, password: String)
}