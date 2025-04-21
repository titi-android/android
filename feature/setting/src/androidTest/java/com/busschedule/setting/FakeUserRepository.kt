package com.busschedule.setting

import com.busschedule.domain.repository.UserRepository
import com.busschedule.model.Token

class FakeUserRepository: UserRepository {
    override suspend fun login(name: String, password: String): Token =
        Token(accessToken = "", refreshToken = "")


    override suspend fun signup(name: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }

    override suspend fun postInquiry(title: String, content: String) {

    }
}