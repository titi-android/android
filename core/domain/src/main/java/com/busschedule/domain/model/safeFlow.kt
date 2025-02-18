//package com.busschedule.domain.model
//
//import android.util.Log
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOn
//import retrofit2.Response
//
//fun <T : Any> safeFlow(apiFunc: suspend () -> Response<ApiResponse<T>>): Flow<com.busschedule.data.network.ApiState<T>> =
//    flow {
//        try {
//            val res = apiFunc.invoke()
//            val apiResult = res.body()!!
//            if (res.isSuccessful && apiResult.success) {
//                emit(com.busschedule.data.network.ApiState.Success(data = apiResult.data ?: throw NullPointerException(), msg = apiResult.message))
//            } else {
//                Log.d("daeyoung", "errorbody: ${res.errorBody()?.string()}")
//                val errorBody = apiResult.message
//                val errorCode = apiResult.code
//                emit(com.busschedule.data.network.ApiState.Error(errorCode, errorBody))
//            }
//        } catch (e: Exception) {
//            emit(com.busschedule.data.network.ApiState.NotResponse(message = e.message ?: "", exception = e))
//        }
//    }.flowOn(Dispatchers.IO)
//
//fun safeFlowAndSaveToken(
//    apiFunc: suspend () -> Response<ApiResponse<Token>>,
//    saveToken: suspend (String) -> Unit,
//): Flow<com.busschedule.data.network.ApiState<Token>> =
//    flow {
//        try {
//            val res = apiFunc.invoke()
//            val apiResult = res.body()!!
//            if (res.isSuccessful && apiResult.success) {
//                val data = res.body()?.data?.accessToken
//                saveToken(data ?: "")
//                emit(com.busschedule.data.network.ApiState.Success(res.body()?.data ?: throw NullPointerException(), msg = apiResult.message))
//            } else {
//                val errorBody = res.errorBody() ?: throw NullPointerException()
//                emit(com.busschedule.data.network.ApiState.Error( apiResult.code, res.body()?.message ?: throw NullPointerException() ))
//            }
//        } catch (e: Exception) {
//            emit(com.busschedule.data.network.ApiState.NotResponse(message = e.message ?: "", exception = e))
//        }
//    }.flowOn(Dispatchers.IO)
//
//fun <T : Any> safeFlowUnit(apiFunc: suspend () -> Response<ApiResponse<T>>): Flow<com.busschedule.data.network.ApiState<Unit>> =
//    flow {
//        try {
//            val res = apiFunc.invoke()
//            val apiResult = res.body()!!
//            if (res.isSuccessful && apiResult.success) {
//                emit(com.busschedule.data.network.ApiState.Success(data = Unit, msg = apiResult.message))
//            } else {
//                val errorBody = apiResult.message
//                emit(com.busschedule.data.network.ApiState.Error(apiResult.code, errorBody))
//            }
//        } catch (e: Exception) {
//            emit(com.busschedule.data.network.ApiState.NotResponse(message = e.message ?: "", exception = e))
//        }
//    }.flowOn(Dispatchers.IO)
//
//fun <T : Any> safeFlowNotJson(apiFunc: suspend () -> T): Flow<com.busschedule.data.network.ApiState<T>> =
//    flow {
//        try {
//            val res = apiFunc()
//                emit(com.busschedule.data.network.ApiState.Success(data = res , msg = ""))
//        } catch (e: Exception) {
//            emit(com.busschedule.data.network.ApiState.NotResponse(message = e.message ?: "", exception = e))
//        }
//    }.flowOn(Dispatchers.IO)