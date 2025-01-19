package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.LoginUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(loginUser: LoginUser): Flow<ApiState<String>>
    fun signup(loginUser: LoginUser): Flow<ApiState<Unit>>
}