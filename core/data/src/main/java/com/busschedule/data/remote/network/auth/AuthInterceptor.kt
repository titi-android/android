package com.busschedule.data.remote.network.auth

import android.util.Log
import com.busschedule.data.local.datastore.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    private val AUTHORIZATION = "Authorization"
    private val NETWORK_ERROR = 401

    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = runBlocking {
            tokenManager.getAccessToken().first()
        } ?: return errorResponse(chain.request())

        val request = chain.request().newBuilder().header(AUTHORIZATION, "Bearer $token").build()

        val response = chain.proceed(request)
        val responseBody = response.body ?: return response

        // 엑세스 토큰이 유효하지 않아도 백엔드에서 HTTP CODE를 200으로 보내고 json code를 JWT407으로 보내는 상황
        if (response.code == HTTP_OK) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer.clone()
            val responseString = buffer.readUtf8() // 복사된 Buffer에서 문자열 추출
            val code = JSONObject(responseString).optString("code")
            if (code == "JWT407") {
                val newRequest = HttpSuccessAuthenticator(tokenManager).authenticate(response)
                Log.d("daeyoung", "newRequest: $newRequest")
                return newRequest?.let { chain.proceed(it) } ?: response
            }
        }

        return response
    }

    private fun errorResponse(request: Request): Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(NETWORK_ERROR)
        .message("엑세스 토큰 없음")
        .body(ResponseBody.create(null, ""))
        .build()
}