package com.busschedule.schedulelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.datastore.TokenManager
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

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val scheduleListUiState = combine(dayOfWeeks, schedules, isLoading) { dayOfWeeks, schedules, isLoading ->
        ScheduleListUiState(dayOfWeeks, schedules, isLoading)
    }

    suspend fun initFCMToken() {
        try {
            if (tokenManager.isExistFCMToken().not()) {
                val token = FirebaseMessaging.getInstance().token.await()
                tokenManager.saveFCMToken(token)
                fetchPostFCMToken(token)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun fetchReadTodaySchedules() {
        viewModelScope.launch {
            readTodaySchedulesUseCase().onSuccess { schedules ->
                _schedules.update { schedules.map { it.asDomain() } }
                _isLoading.update { false }
            }.onFailure {  }
        }
    }

    fun fetchReadDayOfWeekSchedules(dayOfWeek: String) {
        viewModelScope.launch {
            readDaysSchedulesUseCase(dayOfWeek).onSuccess { schedules ->
                _schedules.update { schedules.map { it.asDomain() } }
            }
        }
    }

    fun fetchDeleteSchedules(scheduleId: Int) {
        viewModelScope.launch {
            deleteScheduleUseCase(scheduleId).onSuccess {
                _schedules.update { schedule -> schedule.filter { it.id != scheduleId } }
            }.onFailure {  }
        }
    }

    private fun fetchPostFCMToken(token: String) {
        viewModelScope.launch { postFCMTokenUseCase(token) }
    }

    fun fetchPutScheduleAlarm(scheduleId: Int, updateAlarm: () -> Unit) {
        viewModelScope.launch {
            putScheduleAlarmUseCase(scheduleId).onSuccess { updateAlarm() }.onFailure {  }

        }
    }
}