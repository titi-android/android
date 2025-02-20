package com.busschedule.domain.repository

import com.busschedule.model.Token

interface UserRepository {
    suspend fun login(name: String, password: String): Token
    suspend fun signup(name: String, password: String)

    suspend fun postInquiry(title: String, content: String)
}