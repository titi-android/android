package com.busschedule.domain.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T : Any> safeFlow(apiFunc: suspend () -> Response<ApiResponse<T>>): Flow<ApiState<T>> =
    flow {
        try {
            val res = apiFunc.invoke()
            val apiResult = res.body()!!
            if (res.isSuccessful && apiResult.status == 200) {
                emit(ApiState.Success(data = apiResult.data ?: throw NullPointerException(), msg = apiResult.message))
            } else {
                val errorBody = apiResult.message
                emit(ApiState.Error(errorBody))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)

fun safeFlowAndSaveToken(
    apiFunc: suspend () -> Response<ApiResponse<String>>,
    saveToken: suspend (String) -> Unit,
): Flow<ApiState<String>> =
    flow {
        try {
            val res = apiFunc.invoke()
            if (res.isSuccessful) {
                val data = res.body()?.data
                saveToken(data ?: "")
                emit(ApiState.Success(res.body()?.data ?: throw NullPointerException()))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(errorBody.string()))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)

fun <T : Any> safeFlowUnit(apiFunc: suspend () -> Response<ApiResponse<T>>): Flow<ApiState<Unit>> =
    flow {
        try {
            val res = apiFunc.invoke()
            if (res.isSuccessful) {
                emit(ApiState.Success(Unit))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(errorBody.string()))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)