package com.busschedule.schedulelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.datastore.TokenManager
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.FCMTokenRequest
import com.busschedule.domain.model.response.schedule.BusSchedule
import com.busschedule.domain.usecase.fcm.PostFCMTokenUseCase
import com.busschedule.domain.usecase.schedule.DeleteScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleAlarmUseCase
import com.busschedule.domain.usecase.schedule.ReadDaysSchedulesUseCase
import com.busschedule.domain.usecase.schedule.ReadTodaySchedulesUseCase
import com.busschedule.schedulelist.model.BusScheduleUi
import com.busschedule.schedulelist.model.ScheduleListUiState
import com.busschedule.schedulelist.model.asDomain
import com.busschedule.util.entity.DayOfWeek
import com.busschedule.util.entity.DayOfWeekUi
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val readTodaySchedulesUseCase: ReadTodaySchedulesUseCase,
    private val readDaysSchedulesUseCase: ReadDaysSchedulesUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase,
    private val putScheduleAlarmUseCase: PutScheduleAlarmUseCase,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _dayOfWeeks = MutableStateFlow(DayOfWeek.entries.map {
        DayOfWeekUi(
            dayOfWeek = it,
            init = LocalDate.now().dayOfWeek.name == it.enName
        )
    })
    val dayOfWeeks: StateFlow<List<DayOfWeekUi>> = _dayOfWeeks.asStateFlow()

    private val _schedules = MutableStateFlow(emptyList<BusScheduleUi>())
    val schedules: StateFlow<List<BusScheduleUi>> = _schedules.asStateFlow()

    val scheduleListUiState = combine(dayOfWeeks, schedules) { dayOfWeeks, schedules ->
        ScheduleListUiState(dayOfWeeks, schedules)
    }

    suspend fun initFCMToken() {
        try {
            if (tokenManager.isExistFCMToken().not()) {
                val token = FirebaseMessaging.getInstance().token.await()
                tokenManager.saveFCMToken(token)
                fetchPostFCMToken(token)
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
                is ApiState.Success<*> -> result.onSuccess {
                    _schedules.update { (result.data as List<BusSchedule>).map { it.asDomain() } }
                }

                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                }
            }
        }
    }

    fun fetchReadDayOfWeekSchedules(dayOfWeek: String) {
        viewModelScope.launch {
            when (val result = readDaysSchedulesUseCase(dayOfWeek).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess {
                    Log.d("daeyoung", "fetchReadDayOfWeekSchedules: $result")
                    _schedules.update { (result.data as List<BusSchedule>).map { it.asDomain() } }
                }

                is ApiState.NotResponse -> {
                }
            }
        }
    }

    fun fetchDeleteSchedules(scheduleId: Int) {
        viewModelScope.launch {
            when (val result = deleteScheduleUseCase(scheduleId).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess {
                    _schedules.update { schedule -> schedule.filter { it.id != scheduleId } }
                    Log.d("daeyoung", "fetchDeleteSchedules: $result")
                }

                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "api NotResponse: ${result.exception}, ${result.message}")
                }
            }
        }
    }

    private fun fetchPostFCMToken(token: String) {
        viewModelScope.launch {
            val fcmToken = FCMTokenRequest(token)
            when (val result = postFCMTokenUseCase(fcmToken).first()) {
                is ApiState.Error -> Log.d("daeyoung", "error: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> {}
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                }
            }
        }
    }

    fun fetchPutScheduleAlarm(scheduleId: Int, onFail: () -> Unit) {
        viewModelScope.launch {
            when (val result = putScheduleAlarmUseCase(scheduleId).first()) {
                is ApiState.Error -> {
                    Log.d("daeyoung", "error: ${result.errMsg}")
                    onFail()
                }

                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> {}
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                }
            }
        }
    }
}