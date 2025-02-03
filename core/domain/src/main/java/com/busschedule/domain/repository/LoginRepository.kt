package com.busschedule.domain.repository

import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.LoginUser
import com.busschedule.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(loginUser: LoginUser): Flow<ApiState<Token>>
    fun signup(loginUser: LoginUser): Flow<ApiState<Unit>>
}