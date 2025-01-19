package com.busschedule.schedulelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.schedule.BusSchedule
import com.busschedule.domain.usecase.schedule.ReadTodaySchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val readTodaySchedulesUseCase: ReadTodaySchedulesUseCase,
) : ViewModel() {

    val scheduleListUiState = MutableStateFlow(emptyList<BusSchedule>())

    fun fetchReadTodaySchedules() {
        viewModelScope.launch {
            when (val result = readTodaySchedulesUseCase().first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { scheduleListUiState.update { result.data as List<BusSchedule> } }
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                    if (result.exception is ConnectException) { }
                }
            }
        }
    }
}