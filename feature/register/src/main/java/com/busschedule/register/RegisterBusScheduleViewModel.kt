package com.busschedule.register

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.busschedule.domain.usecase.bus.ReadAllBusOfBusStopUseCase
import com.busschedule.domain.usecase.busstop.ReadAllBusStopUseCase
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleUseCase
import com.busschedule.domain.usecase.schedule.ReadScheduleUseCase
import com.busschedule.model.BusInfo
import com.busschedule.model.BusType
import com.busschedule.navigation.Route
import com.busschedule.register.entity.AddBusDialogUiState
import com.busschedule.register.entity.Bus
import com.busschedule.register.entity.BusStopInfoUI
import com.busschedule.register.entity.CityOfRegion
import com.busschedule.register.entity.KakaoMapObject
import com.busschedule.register.entity.ScheduleRegister
import com.busschedule.register.entity.SelectRegionUiState
import com.busschedule.register.entity.SelectedBusUI
import com.busschedule.util.entity.DayOfWeek
import com.busschedule.util.entity.DayOfWeekUi
import com.busschedule.widget.widget.worker.ScheduleWorker
import com.kakao.vectormap.KakaoMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    private val _selectBusStopInfoUI = MutableStateFlow<BusStopInfoUI?>(null)
    val selectBusStopInfoUI: StateFlow<BusStopInfoUI?> = _selectBusStopInfoUI

    val registerBusScheduleUiState =
        combine(
            scheduleName,
            dayOfWeeks,
            startTime,
            endTime,
            isNotify,
            cityOfRegion,
            selectBusStopInfoUI
        ) {
            ScheduleRegister(
                name = scheduleName.value,
                dayOfWeeks = dayOfWeeks.value,
                startTime = startTime.value,
                endTime = endTime.value,
                isNotify = isNotify.value,
                regionName = cityOfRegion.value.getSelectedCityName(),
                busStopInfoUI = selectBusStopInfoUI.value
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

    fun addBusStopInSelectBusStopInfo(popBackStack: () -> Unit) {
        _selectBusStopInfoUI.update {
            val bus = busStop.value
            BusStopInfoUI(
                busStop = bus.busStop,
                nodeId = bus.nodeId,
                busesInit = bus.buses.filter { it.isSelected }
                    .map { BusInfo(name = it.name, type = it.type.name) })
        }
        popBackStack()
    }

    fun initKakaoMap(map: KakaoMap): KakaoMapObject {
        kakaoMap = KakaoMapObject(map)
        return kakaoMap
    }

    private fun updateWidget() {
        Log.d("daeyoung", "업데이트 위젯")
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<ScheduleWorker>().build()
        )
    }

    private fun fetchPostBusSchedule(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        viewModelScope.launch {
            postScheduleUseCase(
                name = scheduleName.value,
                daysList = dayOfWeeks.value.filter { it.isSelected }
                    .map { "${it.dayOfWeek.value}요일" },
                startTime = startTime.value,
                endTime = endTime.value,
                regionName = cityOfRegion.value.getSelectedCityName(),
                busStopName = selectBusStopInfoUI.value?.busStop ?: "",
                nodeId = selectBusStopInfoUI.value?.nodeId ?: "",
                busInfos = selectBusStopInfoUI.value?.getBuses() ?: emptyList(),
                isAlarmOn = isNotify.value
            ).onSuccess {
                onSuccess()
                updateWidget()
            }.onFailure {}
        }
    }

    // 이미 지역이 정해져 있을 때 지도 화면 출력 시 한번 호출하는 함수
    fun fetchFirstReadAllBusStop(region: String, busStop: String, changeLoadingState: (Boolean) -> Unit) {
        viewModelScope.launch {
            readAllBusStopUseCase(cityName = region, nodeId = busStop).onSuccess {
                kakaoMap.removeAndAddLabel(
                    icon = com.busschedule.designsystem.R.drawable.image_busstop_label,
                    labels = it
                )
                changeLoadingState(true)
            }.onFailure {}
        }
    }

    fun fetchReadAllBusStop(busStopName: String) {
        viewModelScope.launch {
            readAllBusStopUseCase(
                cityName = cityOfRegion.value.getSelectedCityName(),
                nodeId = busStopName
            ).onSuccess { busStop ->
                if (busStop.isNotEmpty()) {
                    kakaoMap.removeAndAddLabel(
                        icon = com.busschedule.designsystem.R.drawable.image_busstop_label,
                        labels = busStop
                    )
                }
            }.onFailure {}
        }
    }

    fun fetchReadAllBusOfBusStop(busStopName: String, nodeId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            Log.d("daeyoung", "before busstop: ${busStop.value}")
            _busStop.update { it.copy(busStop = busStopName, nodeId = nodeId) }
            readAllBusOfBusStopUseCase(
                cityName = cityOfRegion.value.getSelectedCityName(),
                busStopId = nodeId
            ).onSuccess { busInfos ->
                Log.d("daeyoung", "after busstop: ${busStop.value}")
                _busStop.update { selectedBusUI ->
                    selectedBusUI.copy(buses = busInfos.map { bus ->
                        Bus(
                            name = bus.name,
                            type = BusType.find(bus.type),
                            selectedInit = selectBusStopInfoUI.value?.getBuses()
                                ?.any { it.name == bus.name && it.type == bus.type } ?: false)
                    })
                }
                onSuccess()
            }.onFailure { Log.d("daeyoung", it.message.toString()) }
        }
    }

    private fun fetchReadSchedule(scheduleId: Int) {
        viewModelScope.launch {
            readScheduleUseCase(scheduleId).onSuccess { scheduleRegister ->
                scheduleRegister.also { res ->
                    _scheduleName.update { res.name }
                    _dayOfWeeks.update {
                        DayOfWeek.entries.map {
                            DayOfWeekUi(
                                dayOfWeek = it,
                                init = res.days.contains("${it.value}요일")
                            )
                        }
                    }
                    _startTime.update { res.startTime }
                    _endTime.update { res.endTime }
                    _isNotify.update { res.isAlarmOn }
                    _cityOfRegion.update { CityOfRegion(initCity = res.regionName) }
                    _selectBusStopInfoUI.update {
                        BusStopInfoUI(
                            busStop = res.busStopName,
                            nodeId = res.nodeId,
                            busesInit = res.busInfos
                        )
                    }
                }
            }.onFailure {}
        }
    }

    private fun fetchPutSchedule(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        viewModelScope.launch {
            putScheduleUseCase(
                scheduleId = scheduleId!!,
                name = scheduleName.value,
                daysList = dayOfWeeks.value.filter { it.isSelected }
                    .map { "${it.dayOfWeek.value}요일" },
                startTime = startTime.value,
                endTime = endTime.value,
                regionName = cityOfRegion.value.getSelectedCityName(),
                busStopName = selectBusStopInfoUI.value?.busStop ?: "",
                nodeId = selectBusStopInfoUI.value?.nodeId ?: "",
                busInfos = selectBusStopInfoUI.value?.getBuses() ?: emptyList(),
                isAlarmOn = isNotify.value
            ).onSuccess {
                onSuccess()
                updateWidget()
            }.onFailure { onFail(it.message!!) }
        }
    }

    fun putOrPostSchedule(
        onSuccessOfPut: () -> Unit,
        onFailOfPut: (String) -> Unit,
        onSuccessOfPost: () -> Unit,
        onFailOfPost: (String) -> Unit,
    ) {
        if (scheduleId != null) {
            fetchPutSchedule(onSuccess = { onSuccessOfPut() }) { onFailOfPut(it) }
            return
        }
        fetchPostBusSchedule(onSuccess = { onSuccessOfPost() }) { onFailOfPost(it) }
    }
}