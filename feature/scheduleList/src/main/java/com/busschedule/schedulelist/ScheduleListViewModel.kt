package com.busschedule.schedulelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.datastore.TokenManager
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.FCMTokenRequest
import com.busschedule.domain.model.response.schedule.BusSchedule
import com.busschedule.domain.usecase.fcm.PostFCMTokenUseCase
import com.busschedule.domain.usecase.schedule.ReadTodaySchedulesUseCase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val readTodaySchedulesUseCase: ReadTodaySchedulesUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase,
    private val tokenManager: TokenManager,
) : ViewModel() {

    val scheduleListUiState = MutableStateFlow(emptyList<BusSchedule>())

    private suspend fun initFCMToken() {
        try {
            if (!tokenManager.isExistFCMToken()) {
                val token = FirebaseMessaging.getInstance().token.await()
                tokenManager.saveFCMToken(token)
            } else {
                Log.d("daeyoung", "FCMToken already exist: ${tokenManager.getFCMToken().first()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun fetchReadTodaySchedules() {
        viewModelScope.launch {
            when (val result = readTodaySchedulesUseCase().first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { scheduleListUiState.update { result.data as List<BusSchedule> } }
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                    if (result.exception is ConnectException) {
                    }
                }
            }
        }
    }

    fun fetchPostFCMToken() {
        viewModelScope.launch {
            initFCMToken()
            val fcmToken = FCMTokenRequest(tokenManager.getFCMToken().first()!!)
            when (val result = postFCMTokenUseCase(fcmToken).first()) {
                is ApiState.Error -> Log.d("daeyoung", "error: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess {
                }

                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                }
            }
        }
    }
}