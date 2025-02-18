package com.busschedule.data.network

import com.busschedule.model.exception.ForbiddenException
import com.busschedule.model.exception.NetworkException
import com.busschedule.model.exception.NotFoundException
import com.busschedule.model.exception.RequestFailException
import com.busschedule.model.exception.ScheduleServerError
import com.busschedule.model.exception.UnknownException

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T, val msg: String = "") : ApiResult<T>
    sealed interface Failure : ApiResult<Nothing> {
        // http error
        data class HttpError(val code: String, val message: String, val body: String) : Failure
        // http success, but failed for a different reason
        data class FailError(val code: String, val message: String) : Failure
        data class NetworkError(val throwable: Throwable) : Failure
        data class UnknownApiError(val throwable: Throwable) : Failure

        fun safeThrowable(): Throwable =
            when (this) {
                is HttpError -> handleHttpError(this)
                is FailError -> handleFailError(this)
                is NetworkError -> throwable
                is UnknownApiError -> throwable
            }
    }

    val isSuccess: Boolean
        get() = this is Success

    val isFailure: Boolean
        get() = this is Failure

    fun getOrThrow(): T {
        throwFailure()
        return (this as Success).data
    }

    fun getOrNull(): T? =
        when (this) {
            is Success -> data
            else -> null
        }

    fun failureOrThrow(): Failure {
        throwOnSuccess()
        return this as Failure
    }

    fun exceptionOrNull(): Throwable? =
        when (this) {
            is Failure -> safeThrowable()
            else -> null
        }

    companion object {
        fun <R> successOf(result: R): ApiResult<R> = Success(result)
    }
}

inline fun <T> ApiResult<T>.onSuccess(
    action: (value: T) -> Unit,
): ApiResult<T> {
    if (isSuccess) action(getOrThrow())
    return this
}

inline fun <T> ApiResult<T>.onFailure(
    action: (error: ApiResult.Failure) -> Unit,
): ApiResult<T> {
    if (isFailure) action(failureOrThrow())
    return this
}

internal fun ApiResult<*>.throwOnSuccess() {
    if (this is ApiResult.Success) throw IllegalStateException("Cannot be called under Success conditions.")
}

internal fun ApiResult<*>.throwFailure() {
    if (this is ApiResult.Failure) {
        throw safeThrowable()
    }
}

private fun handleHttpError(httpError: ApiResult.Failure.HttpError): Exception = runCatching {
    com.busschedule.data.Json.getScheduleErrorBody(httpError.body)
}.getOrNull()?.run {
    handleScheduleError(httpStatusCode = httpError.code, scheduleErrorResponse = this)
} ?: handleNonScheduleError(httpError.code)

private fun handleScheduleError(httpStatusCode: String, scheduleErrorResponse: ScheduleErrorResponse): Exception = runCatching {
    ScheduleServerError.valueOf(scheduleErrorResponse.errorCode).exception
}.getOrNull() ?: handleNonScheduleError(httpStatusCode)

private fun handleNonScheduleError(httpStatusCode: String) = when (httpStatusCode) {
    "400" -> RequestFailException()
    "403" -> ForbiddenException()
    "404" -> NotFoundException()
    "500", "501", "502", "503", "504", "505" -> NetworkException()
    else -> UnknownException()
}

private fun handleFailError(failError: ApiResult.Failure.FailError): Exception = ScheduleServerError.find(failError.code).exception