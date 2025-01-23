package com.busschedule.register

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.request.ScheduleRegisterRequest
import com.busschedule.domain.model.response.schedule.Time
import com.busschedule.domain.usecase.busstop.CheckBusStopUseCase
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.register.constant.Region
import com.busschedule.register.entity.CityOfRegion
import com.busschedule.register.entity.ScheduleRegister
import com.busschedule.register.entity.SupportingBusStopText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterBusScheduleViewModel @Inject constructor(
    private val postScheduleUseCase: PostScheduleUseCase,
    private val checkBusStopUseCase: CheckBusStopUseCase
) : ViewModel() {
    private val _scheduleName = MutableStateFlow("")
    val scheduleName: StateFlow<String> = _scheduleName.asStateFlow()

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date.asStateFlow()

    private val _startTime = MutableStateFlow(Time())
    val startTime: StateFlow<Time> = _startTime.asStateFlow()

    private val _endTime = MutableStateFlow(Time())
    val endTime: StateFlow<Time> = _endTime.asStateFlow()

    private val _busStop = MutableStateFlow("")
    val busStop: StateFlow<String> = _busStop.asStateFlow()

    private val _busStopSupporting = MutableStateFlow(SupportingBusStopText())
    val busStopSupporting: StateFlow<SupportingBusStopText> = _busStopSupporting.asStateFlow()

    private val _bus = MutableStateFlow("")
    val bus: StateFlow<String> = _bus.asStateFlow()

    private val _cityOfRegion = MutableStateFlow(CityOfRegion(Region.entries))
    val cityOfRegion: StateFlow<CityOfRegion> = _cityOfRegion.asStateFlow()

    val registerBusScheduleUiState =
        combine(scheduleName, date, startTime, endTime, cityOfRegion, busStop, busStopSupporting, bus) {
            ScheduleRegister(
                name = scheduleName.value,
                days = date.value,
                startTime = startTime.value,
                endTime = endTime.value,
                regionName = cityOfRegion.value.getSelectedCityName(),
                busStopName = busStop.value,
                busStopSupportingName = busStopSupporting.value,
                // TODO: 버스 여러개 등록하는 ui로 변경
                busList = emptyList()
            )
        }

    fun setScheduleName(name: String) {
        _scheduleName.update { name }
    }

    fun setDate(date: String) {
        _date.update { date }
    }

    fun setStartTime(time: Time) {
        _startTime.update { time }
    }

    fun setEndTime(time: Time) {
        _endTime.update { time }
    }

    fun setBusStop(busStop: String) {
        _busStop.update { busStop }
    }

    fun setBus(bus: String) {
        _bus.update { bus }
    }

    fun fetchPostBusSchedule(scheduleRegisterRequest: ScheduleRegisterRequest) {
        viewModelScope.launch {
            when (val result = postScheduleUseCase(scheduleRegisterRequest).first()) {
                is ApiState.Error -> Log.d("daeyoung", "error : ${result.errMsg}")
                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d("daeyoung", "not response: ${result.message}, ${result.exception}")
                is ApiState.Success -> {}
            }
        }
    }

    fun fetchCheckBusStop(cityName: String, busStopName: String) {
        viewModelScope.launch {
            when (val result = checkBusStopUseCase(cityName, busStopName).first()) {
                is ApiState.Error -> _busStopSupporting.update { it.copy(resultMsg = result.errMsg, color = Color.Red) }
                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d("daeyoung", "not response: ${result.message}, ${result.exception}")
                is ApiState.Success -> {_busStopSupporting.update {it.copy(resultMsg = result.msg, color = Color.Green)} }
            }
        }
    }



}