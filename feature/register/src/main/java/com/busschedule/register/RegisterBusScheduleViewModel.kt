package com.busschedule.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.response.busstop.BusInfo
import com.busschedule.domain.model.response.schedule.ScheduleRegisterResponse
import com.busschedule.domain.usecase.bus.ReadAllBusOfBusStopUseCase
import com.busschedule.domain.usecase.busstop.ReadAllBusStopUseCase
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleUseCase
import com.busschedule.domain.usecase.schedule.ReadScheduleUseCase
import com.busschedule.register.entity.AddBusDialogUiState
import com.busschedule.register.entity.Bus
import com.busschedule.register.entity.BusStopInfo
import com.busschedule.register.entity.CityOfRegion
import com.busschedule.register.entity.KakaoMapObject
import com.busschedule.register.entity.ScheduleRegister
import com.busschedule.register.entity.SelectRegionUiState
import com.busschedule.register.entity.SelectedBusUI
import com.busschedule.register.entity.asDomain
import com.busschedule.util.entity.BusType
import com.busschedule.util.entity.DayOfWeek
import com.busschedule.util.entity.DayOfWeekUi
import com.busschedule.util.entity.navigation.Route
import com.busschedule.util.ext.toFormatTime
import com.kakao.vectormap.KakaoMap
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
    private val readAllBusOfBusStopUseCase: ReadAllBusOfBusStopUseCase,
    private val readScheduleUseCase: ReadScheduleUseCase,
    private val putScheduleUseCase: PutScheduleUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val scheduleId = savedStateHandle.toRoute<Route.RegisterGraph.RegisterSchedule>().id

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

    private val _cityOfRegion = MutableStateFlow(CityOfRegion())
    val cityOfRegion: StateFlow<CityOfRegion> = _cityOfRegion.asStateFlow()

    private val _selectBusStopInfo = MutableStateFlow<BusStopInfo?>(null)
    val selectBusStopInfo: StateFlow<BusStopInfo?> = _selectBusStopInfo

    val registerBusScheduleUiState =
        combine(
            scheduleName,
            dayOfWeeks,
            startTime,
            endTime,
            isNotify,
            cityOfRegion,
            selectBusStopInfo
        ) {
            ScheduleRegister(
                name = scheduleName.value,
                dayOfWeeks = dayOfWeeks.value,
                startTime = startTime.value,
                endTime = endTime.value,
                isNotify = isNotify.value,
                regionName = cityOfRegion.value.getSelectedCityName(),
                busStopInfo = selectBusStopInfo.value
            )
        }

    private val _regionInput =
        MutableStateFlow(savedStateHandle.toRoute<Route.RegisterGraph.SelectBusStop>().busStop)
    val regionInput: StateFlow<String> = _regionInput.asStateFlow()

    val selectRegionUiState = combine(regionInput, cityOfRegion) { input, cityOfRegion ->
        SelectRegionUiState(
            input = input,
            region = cityOfRegion
        )
    }

    private val _busStopInput = MutableStateFlow("")
    val busStopInput: StateFlow<String> = _busStopInput.asStateFlow()

    private val _busStop = MutableStateFlow(SelectedBusUI())
    val busStop: StateFlow<SelectedBusUI> = _busStop.asStateFlow()

    private val _addBusInput = MutableStateFlow("")
    val addBusInput: StateFlow<String> = _addBusInput.asStateFlow()

    private val _addBus = MutableStateFlow(emptyList<Bus>())
    val addBus: StateFlow<List<Bus>> = _addBus.asStateFlow()

    val addBusDialogUiState = combine(addBusInput, addBus) { input, addBus ->
        AddBusDialogUiState(input = input, bus = addBus)
    }

    lateinit var kakaoMap: KakaoMapObject

    init {
        if (scheduleId != null) viewModelScope.launch { fetchReadSchedule(scheduleId) }
    }

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

    fun updateBusStopInput(input: String) {
        _busStopInput.update { input }
    }


    fun updateRegionInput(input: String) {
        _regionInput.update { input }
    }

    fun updateAddBusInput(input: String) {
        _addBusInput.update { input }
    }

    fun addDialogAddBus(showToast: (String) -> Unit) {
        if (addBus.value.find { it.name == addBusInput.value } != null) {
            showToast("이미 추가된 버스입니다.")
            return
        }
        _addBus.update { it + Bus(name = addBusInput.value, selectedInit = true) }
        updateAddBusInput("")
    }

    fun initDialogAddBus() {
        _addBus.update { emptyList() }
        updateAddBusInput("")
    }

    fun addDialogAddBusInBusStop() {
        _busStop.update { busStop.value.copy(buses = it.buses + addBus.value.filter { addB -> addB.isSelected }) }
        _addBus.update { emptyList() }
    }

    fun addBusStopInSelectBusStopInfo(navigate: (Int?) -> Unit) {
        _selectBusStopInfo.update {
            val bus = busStop.value
            BusStopInfo(
                busStop = bus.busStop,
                nodeId = bus.nodeId,
                busesInit = bus.buses.filter { it.isSelected }
                    .map { BusInfo(name = it.name, type = it.type.name) })
        }
        navigate(scheduleId)
    }

    fun initKakaoMap(map: KakaoMap): KakaoMapObject {
        kakaoMap = KakaoMapObject(map)
        return kakaoMap
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

    // 이미 지역이 정해져 있을 때 지도 화면 출력 시 한번 호출하는 함수
    fun fetchFirstReadAllBusStop() {
        viewModelScope.launch {
            when (val result = readAllBusStopUseCase(
                cityOfRegion.value.getSelectedCityName(),
                selectBusStopInfo.value!!.busStop
            ).first()) {
                is ApiState.Error -> {}

                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d(
                    "daeyoung",
                    "not response: ${result.message}, ${result.exception}"
                )

                is ApiState.Success -> {
                    kakaoMap.removeAndAddLabel(
                        icon = core.designsystem.R.drawable.image_busstop_label,
                        labels = result.data.busInfosResponse
                    )
                }
            }
        }
    }

    fun fetchReadAllBusStop(busStopName: String) {
        viewModelScope.launch {
            when (val result = readAllBusStopUseCase(
                cityOfRegion.value.getSelectedCityName(),
                busStopName
            ).first()) {
                is ApiState.Error -> {}

                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d(
                    "daeyoung",
                    "not response: ${result.message}, ${result.exception}"
                )

                is ApiState.Success -> {
                    kakaoMap.removeAndAddLabel(
                        icon = core.designsystem.R.drawable.image_busstop_label,
                        labels = result.data.busInfosResponse,
                    )
                }
            }
        }
    }

    fun fetchReadAllBusOfBusStop(busStopName: String, nodeId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _busStop.update { it.copy(busStop = busStopName, nodeId = nodeId) }
            when (val result = readAllBusOfBusStopUseCase(
                cityOfRegion.value.getSelectedCityName(),
                nodeId
            ).first()) {
                is ApiState.Error -> {
                    Log.d("daeyoung", "error: ${result.errMsg}")
                }

                ApiState.Loading -> {}
                is ApiState.NotResponse -> Log.d(
                    "daeyoung",
                    "not response: ${result.message}, ${result.exception}"
                )

                is ApiState.Success -> {
                    _busStop.update {
                        it.copy(buses = result.data.map { bus ->
                            Bus(bus.name, BusType.find(bus.type))
                        })
                    }
                    onSuccess()
                }
            }
        }
    }

    private fun fetchReadSchedule(scheduleId: Int) {
        viewModelScope.launch {
            when (val result = readScheduleUseCase(scheduleId).first()) {
                is ApiState.Error -> {
                    Log.d("daeyoung", "error: ${result.errMsg}")
                }

                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> {
                    (result.data as ScheduleRegisterResponse).also { res ->
                        _scheduleName.update { res.name }
                        _dayOfWeeks.update {
                            DayOfWeek.entries.map {
                                DayOfWeekUi(
                                    dayOfWeek = it,
                                    init = res.days.contains("${it.value}요일")
                                )
                            }
                        }
                        _startTime.update { res.startTime.toFormatTime() }
                        _endTime.update { res.endTime.toFormatTime() }
                        _isNotify.update { res.isAlarmOn }
                        _cityOfRegion.update { CityOfRegion(initCity = res.regionName) }
                        _selectBusStopInfo.update {
                            BusStopInfo(
                                busStop = res.busStopName,
                                nodeId = res.nodeId,
                                busesInit = res.busInfos
                            )
                        }
                    }
                }

                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                }
            }
        }
    }

    fun fetchPutSchedule(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val scheduleReq = registerBusScheduleUiState.first().asDomain()
            when (val result = putScheduleUseCase(scheduleId!!, scheduleReq).first()) {
                is ApiState.Error -> {
                    Log.d("daeyoung", "error: ${result.errMsg}")
                }

                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> {
                    onSuccess()
                }

                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                }
            }
        }
    }
}