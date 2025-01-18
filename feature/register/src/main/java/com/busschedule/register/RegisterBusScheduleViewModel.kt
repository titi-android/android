package com.busschedule.register

import androidx.lifecycle.ViewModel
import com.busschedule.register.entity.ScheduleRangeTime
import com.busschedule.register.entity.ScheduleRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterBusScheduleViewModel @Inject constructor(): ViewModel() {
    private val _scheduleName = MutableStateFlow("")
    val scheduleName: StateFlow<String> = _scheduleName.asStateFlow()

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date.asStateFlow()

    private val _time = MutableStateFlow(ScheduleRangeTime())
    val time: StateFlow<ScheduleRangeTime> = _time.asStateFlow()

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    private val _busStop = MutableStateFlow("")
    val busStop: StateFlow<String> = _busStop.asStateFlow()

    private val _bus = MutableStateFlow("")
    val bus: StateFlow<String> = _bus.asStateFlow()

    val registerBusScheduleUiState = combine(scheduleName, date, time, city, busStop, bus) {
        ScheduleRegister(
            name = scheduleName.value,
            date = date.value,
            time = time.value,
            city = city.value,
            busStop = busStop.value,
            bus = bus.value
        )
    }

    fun setScheduleName(name: String) {
        _scheduleName.update { name }
    }
    fun setDate(date: String) {
        _date.update { date }
    }

    fun setStartTime(time: String) {
        _time.update { it.copy(start = time) }
    }

    fun setEndTime(time: String) {
        _time.update { it.copy(end = time) }
    }

    fun setCity(city: String) {
        _city.update { city }
    }

    fun setBusStop(busStop: String) {
        _busStop.update { busStop }
    }

    fun setBus(bus: String) {
        _bus.update { bus }
    }




}