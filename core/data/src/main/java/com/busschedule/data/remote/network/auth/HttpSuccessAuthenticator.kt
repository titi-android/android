package com.busschedule.data.remote.network.auth

import android.util.Log
import com.busschedule.data.local.datastore.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class HttpSuccessAuthenticator(private val tokenManager: TokenManager) {
    private val REFRESH_TOKEN = "Refresh-Token"

    fun authenticate(response: Response): Request? {
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }
        Log.d("daeyoung", "refreshToken : $refreshToken")

        if (refreshToken == null || refreshToken == "LOGIN") {
//            response.close()
            return null
        }

        return newRequestWithToken(refreshToken, response.request)
    }

    private fun newRequestWithToken(token: String, request: Request): Request {
        val newUrl = request.url.newBuilder()
            .encodedPath("/api/v1/users/refresh") // 기존 Base URL 유지, 경로만 변경
            .build()

        return request.newBuilder()
            .url(newUrl)
            .header(REFRESH_TOKEN, token)
            .post(RequestBody.create(null, ByteArray(0)))
            .build()
    }
}