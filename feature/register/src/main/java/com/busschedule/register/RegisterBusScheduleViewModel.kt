package com.busschedule.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.response.busstop.BusStop
import com.busschedule.domain.usecase.bus.ReadAllBusUseCase
import com.busschedule.domain.usecase.busstop.ReadAllBusStopUseCase
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.register.entity.BusStopInfo
import com.busschedule.register.entity.CityOfRegion
import com.busschedule.register.entity.ScheduleRegister
import com.busschedule.register.entity.SelectBusUiState
import com.busschedule.register.entity.SelectRegionUiState
import com.busschedule.register.entity.asDomain
import com.busschedule.util.entity.DayOfWeek
import com.busschedule.util.entity.DayOfWeekUi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
    private val postScheduleUseCase: PostScheduleUseCase,
    private val readAllBusStopUseCase: ReadAllBusStopUseCase,
    private val readAllBusUseCase: ReadAllBusUseCase,
) : ViewModel() {
    private val _scheduleName = MutableStateFlow("")
    val scheduleName: StateFlow<String> = _scheduleName.asStateFlow()

    private val _dayOfWeeks =
        MutableStateFlow(DayOfWeek.entries.map { DayOfWeekUi(dayOfWeek = it, init = false) })
    val dayOfWeeks: StateFlow<List<DayOfWeekUi>> = _dayOfWeeks.asStateFlow()

    private val _startTime = MutableStateFlow("시작 시간")
    val startTime: StateFlow<String> = _startTime.asStateFlow()

    private val _endTime = MutableStateFlow("종료 시간")
    val endTime: StateFlow<String> = _endTime.asStateFlow()

    private val _isNotify = MutableStateFlow(false)
    val isNotify: StateFlow<Boolean> = _isNotify.asStateFlow()

    private val _busStop = MutableStateFlow("버스 정류장")
    val busStop: StateFlow<String> = _busStop.asStateFlow()

    private val _cityOfRegion = MutableStateFlow(CityOfRegion())
    val cityOfRegion: StateFlow<CityOfRegion> = _cityOfRegion.asStateFlow()

    val registerBusScheduleUiState =
        combine(
            scheduleName,
            dayOfWeeks,
            startTime,
            endTime,
            isNotify,
            cityOfRegion,
            busStop,
//            bus
        ) {
            ScheduleRegister(
                name = scheduleName.value,
                dayOfWeeks = dayOfWeeks.value,
                startTime = startTime.value,
                endTime = endTime.value,
                isNotify = isNotify.value,
                regionName = cityOfRegion.value.getSelectedCityName(),
                busStopName = busStop.value,
                // TODO: 버스 여러개 등록하는 ui로 변경
//                busList = emptyList()
            )
        }

    private val _regionInput = MutableStateFlow("")
    val regionInput: StateFlow<String> = _regionInput.asStateFlow()

    val selectRegionUiState = combine(regionInput, cityOfRegion) { input, cityOfRegion ->
        SelectRegionUiState(
            input = input,
            region = cityOfRegion
        )
    }

    private val _busStopInput = MutableStateFlow("")
    private val busStopInput: StateFlow<String> = _busStopInput.asStateFlow()

    private val _busStopInfo = MutableStateFlow(emptyList<BusStopInfo>())
    private val busStopInfo: StateFlow<List<BusStopInfo>> = _busStopInfo.asStateFlow()

    val selectBusUiState = combine(busStopInput, busStopInfo) { input, busStop ->
        SelectBusUiState(
            input = input,
            busStop = busStop
        )
    }

    private var busStopNodeId = BusStop()

    fun updateScheduleName(name: String) {
        _scheduleName.update { name }
    }

    fun updateStartTime(time: String) {
        _startTime.update { time }
    }

    fun updateEndTime(time: String) {
        _endTime.update { time }
    }

    fun updateIsNotify() {
        _isNotify.update { !isNotify.value }
    }

    fun setBusStop(busStop: String) {
        _busStop.update { busStop }
    }

    fun updateBusStopInput(input: String) {
        _busStopInput.update { input }
    }

//    fun setBus(bus: String) {
//        _bus.update { bus }
//    }

    fun updateRegionInput(input: String) {
        _regionInput.update { input }
    }

    fun fetchPostBusSchedule(scheduleRegister: ScheduleRegister) {

        viewModelScope.launch {
            when (val result = postScheduleUseCase(scheduleRegister.asDomain()).first()) {
                is ApiState.Error -> Log.d("daeyoung", "error : ${result.errMsg}")
                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d(
                    "daeyoung",
                    "not response: ${result.message}, ${result.exception}"
                )

                is ApiState.Success -> {
                    Toast.makeText(context, "스케줄이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun fetchReadAllBusStop(busStopName: String, showToastMsg: (String) -> Unit) {
        viewModelScope.launch {
            when (val result = readAllBusStopUseCase(cityOfRegion.value.getSelectedCityName(), busStopName).first()) {
                is ApiState.Error -> { showToastMsg(result.errMsg) }
                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d(
                    "daeyoung",
                    "not response: ${result.message}, ${result.exception}"
                )

                is ApiState.Success -> {
                    _busStopInfo.update { result.data.busInfosResponse.map { it.asDomain() } }
                    Log.d("daeyoung", "success: ${result.data}")
                }
            }
        }
    }

    fun fetchReadAllBus() {
        viewModelScope.launch {
//            when (val result = readAllBusUseCase(
//                cityOfRegion.value.getSelectedCityName(),
//                busStopNodeId.nodeId
//            ).first()) {
//                is ApiState.Error -> {
//                    Log.d("daeyoung", "error: ${result.errMsg}")
//                }
//
//                ApiState.Loading -> {}
//                is ApiState.NotResponse -> Log.d(
//                    "daeyoung",
//                    "not response: ${result.message}, ${result.exception}"
//                )
//
//                is ApiState.Success -> {
//                    _bus.update { result.data.map { Bus(it) } }
//                    Log.d("daeyoung", "success: ${bus.value}")
//                }
//            }
        }
    }


}