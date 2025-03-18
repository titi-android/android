package com.busschedule.data.remote.network.auth

import android.util.Log
import com.busschedule.data.local.datastore.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okhttp3.Response

class HttpSuccessAuthenticator(private val tokenManager: TokenManager) {
    private val AUTHORIZATION = "Authorization"

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

    private fun newRequestWithToken(token: String, request: Request): Request =
        request.newBuilder()
            .header(AUTHORIZATION, "Bearer $token")
            .build()
}