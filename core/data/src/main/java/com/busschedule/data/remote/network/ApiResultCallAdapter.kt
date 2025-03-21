package com.busschedule.data.remote.network

import android.util.Log
import com.busschedule.data.remote.model.ApiResult
import com.busschedule.domain.model.ApiResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

internal class ApiResultCallAdapter<R>(
    private val successType: Type,
) : CallAdapter<R, Call<ApiResult<R>>> {
    override fun adapt(call: Call<R>): Call<ApiResult<R>> = ApiResultCall(call, successType)
    override fun responseType(): Type = successType
}

private class ApiResultCall<R>(
    private val delegate: Call<R>,
    private val successType: Type,
) : Call<ApiResult<R>> {

    override fun enqueue(callback: Callback<ApiResult<R>>) = delegate.enqueue(
        object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@ApiResultCall, Response.success(response.toApiResult()))
            }

            private fun Response<R>.toApiResult(): ApiResult<R> {
                if (!isSuccessful) { // Http 응답 에러
                    val errorBody = errorBody()?.string()
                    return ApiResult.Failure.HttpError(
                        code = code().toString(),
                        message = message(),
                        body = errorBody ?: "Unknown http response error",
                    )
                }
                // 모든 버스들 불러오는 api(/api/v1/nodes/bus-names/all) 때문에 사용
                if ( body() !is ApiResponse<*> ) {
                    body()?.let { body -> return ApiResult.successOf(body) }
                }
                val isSuccess = (body() as ApiResponse<*>).success
                if (isSuccess) {
                    body()?.let { body -> return ApiResult.successOf(body) }
                } else {
                    Log.d("daeyoung", "ApiResultCall not success: ${body()}")
                    val errorBody = (body() as ApiResponse<*>)
                    return ApiResult.Failure.FailError(
                        code = errorBody.code,
                        message = errorBody.message,
                    )
                }

                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    (ApiResult.successOf(Unit as R))
                } else {
                    ApiResult.Failure.UnknownApiError(
                        IllegalStateException(
                            "Body가 존재하지 않지만, Unit 이외의 타입으로 정의했습니다. ApiResult<Unit>로 정의하세요.",
                        ),
                    )
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
//                Log.d("ApiResultCall", "onFailure: $throwable")
                val error = if (throwable is IOException) {
                    ApiResult.Failure.NetworkError(throwable)
                } else {
                    ApiResult.Failure.UnknownApiError(throwable)
                }
                callback.onResponse(this@ApiResultCall, Response.success(error))
            }
        },
    )

    override fun clone(): Call<ApiResult<R>> = ApiResultCall(delegate.clone(), successType)

    override fun execute(): Response<ApiResult<R>> =
        throw UnsupportedOperationException("This adapter doesn't support sync execution")

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}