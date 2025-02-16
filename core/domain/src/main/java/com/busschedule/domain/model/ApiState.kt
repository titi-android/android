package com.busschedule.domain.model

sealed class ApiState<out T : Any> {
    data class Success<T : Any>(val data: T, val msg: String = "") : ApiState<T>()
    data class Error(val errMsg: String) : ApiState<Nothing>()
    data class NotResponse(val message: String?, val exception: Throwable? = null) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

    fun onSuccess(onSuccess: (T) -> Unit) {
        if (this is Success) {
            onSuccess(this@ApiState.data)
        }
    }

    fun onError(onError: (String) -> Unit) {
        if (this is Error) {
            onError(this@ApiState.errMsg)
        }
//        if (this is NotResponse) {
//            onError(ErrorResponse("500", false, "네트워크 오류가 발생했습니다."))
//        }
//        if (this is NotResponse) {
//            onError(ErrorResponse("500", false, "네트워크 오류가 발생했습니다."))
//            onError()
//        }
    }

//    fun setSafeNotResponse(e: Exception): Exception {
//        return if (e is ConnectException) {
//            this(NetWorkException("네트워크 연결이 불안정합니다."))
//        } else {
//            e
//        }
//    }

    fun onLoading(onLoading: () -> Unit) {
        if (this is Loading) {
            onLoading()
        }
    }

}

class NetWorkException(message: String) : Exception(message)