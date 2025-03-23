package com.busschedule.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.busschedule.domain.repository.RecentlySearchBusStopRepository
import com.busschedule.domain.repository.TempSaveScheduleRepository
import com.busschedule.domain.usecase.bus.ReadAllBusOfBusStopUseCase
import com.busschedule.domain.usecase.busstop.ReadAllBusStopUseCase
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleUseCase
import com.busschedule.domain.usecase.schedule.ReadScheduleUseCase
import com.busschedule.model.BusInfo
import com.busschedule.model.BusStop
import com.busschedule.model.DayOfWeekUi
import com.busschedule.model.RecentlySearchBusStop
import com.busschedule.model.asBusStop
import com.busschedule.model.asDestinationInfo
import com.busschedule.model.constant.BusType
import com.busschedule.model.constant.DayOfWeek
import com.busschedule.navigation.Route
import com.busschedule.register.model.AddBusDialogUiState
import com.busschedule.register.model.Bus
import com.busschedule.register.model.BusStopInfoUI
import com.busschedule.register.model.BusStopInfoUIFactory
import com.busschedule.register.model.CityOfRegion
import com.busschedule.register.model.KakaoMapObject
import com.busschedule.register.model.ScheduleRegister
import com.busschedule.register.model.SelectBusStopUiState
import com.busschedule.register.model.SelectRegionUiState
import com.busschedule.register.model.SelectedBusUI
import com.busschedule.register.model.asRouteInfo
import com.busschedule.widget.widget.worker.ScheduleWorker
import com.kakao.vectormap.KakaoMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterBusScheduleViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postScheduleUseCase: PostScheduleUseCase,
    private val readAllBusStopUseCase: ReadAllBusStopUseCase,
    private val readAllBusOfBusStopUseCase: ReadAllBusOfBusStopUseCase,
    private val readScheduleUseCase: ReadScheduleUseCase,
    private val putScheduleUseCase: PutScheduleUseCase,
    private val recentlySearchBusStopRepository: RecentlySearchBusStopRepository,
    private val tempSaveScheduleRepository: TempSaveScheduleRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val scheduleId = savedStateHandle.toRoute<Route.RegisterGraph.RegisterSchedule>().id
    private val isExistTempSchedule = savedStateHandle.toRoute<Route.RegisterGraph.RegisterSchedule>().isExistTempSchedule

    private val selectDayOfWeek =
        savedStateHandle.toRoute<Route.RegisterGraph.RegisterSchedule>().dayOfWeek

    private val _scheduleName = MutableStateFlow("")
    val scheduleName: StateFlow<String> = _scheduleName.asStateFlow()

    private val _dayOfWeeks =
        MutableStateFlow(DayOfWeek.entries.map {
            DayOfWeekUi(dayOfWeek = it, init = isEqualDayOfWeek(it, selectDayOfWeek))
        })
    val dayOfWeeks: StateFlow<List<DayOfWeekUi>> = _dayOfWeeks.asStateFlow()

    private val _startTime = MutableStateFlow("시작 시간")
    val startTime: StateFlow<String> = _startTime.asStateFlow()

    private val _endTime = MutableStateFlow("종료 시간")
    val endTime: StateFlow<String> = _endTime.asStateFlow()

    private val _isNotify = MutableStateFlow(false)
    val isNotify: StateFlow<Boolean> = _isNotify.asStateFlow()

    private var _routeInfos = mutableStateListOf(BusStopInfoUIFactory.create())
    val routeInfos: List<BusStopInfoUI> = _routeInfos

    private val _arriveBusStop = MutableStateFlow(BusStop())
    val arriveBusStop: StateFlow<BusStop> = _arriveBusStop

    val registerBusScheduleUiState =
        combine(scheduleName, dayOfWeeks, startTime, endTime, isNotify, arriveBusStop)
        {
            ScheduleRegister(
                name = scheduleName.value,
                dayOfWeeks = dayOfWeeks.value,
                startTime = startTime.value,
                endTime = endTime.value,
                isNotify = isNotify.value,
                arriveBusStop = arriveBusStop.value
            )
        }

    private val _regionInput = MutableStateFlow("")
    val regionInput: StateFlow<String> = _regionInput.asStateFlow()

    private val _cityOfRegion = MutableStateFlow(CityOfRegion())
    val cityOfRegion: StateFlow<CityOfRegion> = _cityOfRegion.asStateFlow()

    val selectRegionUiState = combine(regionInput, cityOfRegion) { input, cityOfRegion ->
        SelectRegionUiState(
            input = input,
            region = cityOfRegion
        )
    }

    private val _busStopInput = MutableStateFlow("")
    val busStopInput: StateFlow<String> = _busStopInput.asStateFlow()

    private val _recentlySearchBusStop = MutableStateFlow(emptyList<RecentlySearchBusStop>())
    val recentlySearchBusStop: StateFlow<List<RecentlySearchBusStop>> =
        _recentlySearchBusStop.asStateFlow()

    private val _busStop = MutableStateFlow(SelectedBusUI())
    val busStop: StateFlow<SelectedBusUI> = _busStop.asStateFlow()

    val selectBusStopUiState = combine(busStopInput, recentlySearchBusStop) { input, search ->
        SelectBusStopUiState(input = input, recentlySearchBusStop = search)
    }

    private val _addBusInput = MutableStateFlow("")
    val addBusInput: StateFlow<String> = _addBusInput.asStateFlow()

    private val _addBus = MutableStateFlow(emptyList<Bus>())
    val addBus: StateFlow<List<Bus>> = _addBus.asStateFlow()


    val addBusDialogUiState = combine(addBusInput, addBus) { input, addBus ->
        AddBusDialogUiState(input = input, bus = addBus)
    }

    lateinit var kakaoMap: KakaoMapObject

    init {
        if (isExistTempSchedule) fetchReadTempSchedule()
        else if (isUpdateSchedule()) viewModelScope.launch {
            fetchReadSchedule(scheduleId!!) { showToastMsg(it) }
        }
    }

    fun isUpdateSchedule(): Boolean = scheduleId != null

    fun isRouteInfoNotEmpty():Boolean {
        if (routeInfos.isEmpty()) return false
        return routeInfos.first().isNotBlank()
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

    fun addBusStopInfoUI() {
        _routeInfos.add(BusStopInfoUIFactory.create())
    }

    fun removeBusStopInfoUI(id: Int) {
        _routeInfos.removeIf { it.compareID(id) }
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

    fun addBusStopInSelectBusStopInfo(id: Int, popBackStack: () -> Unit) {
        val bus = busStop.value
        val index = _routeInfos.indexOfFirst { it.compareID(id) }
        Log.d("daeyoung", "index: $index")
        _routeInfos[index] = _routeInfos[index].copy(
            region = bus.region,
            busStop = bus.busStop,
            nodeId = bus.nodeId,
            busesInit = bus.buses.filter { it.isSelected }
                .map { BusInfo(name = it.name, type = it.type.name) }
        )
        cityOfRegion.value.unAllSelect()
        _busStopInput.update { "" }
        _busStop.update { SelectedBusUI() }
        popBackStack()
    }

    fun completeOfArriveBusStop(region: String, popBackStack: () -> Unit) {
        val bus = busStop.value
        _arriveBusStop.update {
            it.copy(
                region = bus.region,
                busStop = bus.busStop,
                nodeId = bus.nodeId
            )
        }
        cityOfRegion.value.unAllSelect()
        popBackStack()
    }

    fun initKakaoMap(map: KakaoMap): KakaoMapObject {
        kakaoMap = KakaoMapObject(map)
        return kakaoMap
    }

    private fun showToastMsg(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setRouteInfos(list: List<BusStopInfoUI>) {
        _routeInfos.clear()
        _routeInfos.addAll(list)
    }

    private fun updateWidget() {
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<ScheduleWorker>().build()
        )
    }

    fun updateBusStop(region: String, busStopName: String, nodeId: String) {
        _busStop.update { it.copy(region = region, busStop = busStopName, nodeId = nodeId) }
    }

    private fun isEqualDayOfWeek(dayOfWeek: DayOfWeek, selectDayOfWeek: String) =
        dayOfWeek.value == selectDayOfWeek.take(1)


    private fun fetchPostBusSchedule(onSuccess: () -> Unit, showToast: (String) -> Unit) {
        viewModelScope.launch {
            postScheduleUseCase(
                name = scheduleName.value,
                daysList = dayOfWeeks.value.filter { it.isSelected }
                    .map { "${it.dayOfWeek.value}요일" },
                startTime = startTime.value,
                endTime = endTime.value,
                routeInfos = routeInfos.map { it.asRouteInfo() },
                destinationInfo = arriveBusStop.value.asDestinationInfo(),
                isAlarmOn = isNotify.value
            ).onSuccess {
                onSuccess()
                showToast("스케줄을 등록했습니다.")
                updateWidget()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun isBiggerStartTime(time: String): Boolean {
        val eTime = time.split(":").map { it.toInt() }
        val sTime = startTime.value.split(":").map { it.toInt() }
        if (eTime[0] < sTime[0]) return false
        if (eTime[0] == sTime[0] && eTime[1] <= sTime[1]) return false
        return true
    }

    // 이미 지역이 정해져 있을 때 지도 화면 출력 시 한번 호출하는 함수
    fun fetchFirstReadAllBusStop(
        region: String,
        busStop: String,
        changeLoadingState: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch {
            readAllBusStopUseCase(cityName = region, nodeId = busStop).onSuccess {
                kakaoMap.removeAndAddLabel(
                    icon = com.busschedule.designsystem.R.drawable.image_busstop_label,
                    labels = it,
                    region = region
                )
                changeLoadingState()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun fetchReadAllBusStop(
        region: String,
        busStopName: String,
        changeLoadingState: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch {
            readAllBusStopUseCase(
                cityName = region,
                nodeId = busStopName
            ).onSuccess { busStop ->
                if (busStop.isNotEmpty()) {
                    kakaoMap.removeAndAddLabel(
                        icon = com.busschedule.designsystem.R.drawable.image_busstop_label,
                        labels = busStop,
                        region = region
                    )
                    fetchInsertRecentlySearchBusStop(region, busStopName)
                } else {
                    showToast("버스 정류장을 찾을 수 없습니다.")
                }
                changeLoadingState()
            }.onFailure {
                changeLoadingState()
                showToast(it.message!!)
            }
        }
    }

    fun fetchReadAllBusOfBusStop(
        id: Int,
        busStopName: String,
        nodeId: String,
        hideBottomSheet: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch {
            readAllBusOfBusStopUseCase(
                cityName = kakaoMap.region,
                busStopId = nodeId
            ).onSuccess { busInfos ->
                _busStop.update {
                    SelectedBusUI(
                        region = kakaoMap.region,
                        busStop = busStopName,
                        nodeId = nodeId,
                        buses = busInfos.map { bus ->
                            val routeInfos = routeInfos.find { it.compareID(id) }
                                Bus(
                                name = bus.name,
                                type = BusType.find(bus.type),
                                nodeId = nodeId,
                                selectedInit = routeInfos?.getBuses()
                                    ?.any { it.name == bus.name && it.type == bus.type && routeInfos.nodeId == nodeId } ?: false
                            )
                        }
                    )
                }
                hideBottomSheet()
            }.onFailure { showToast(it.message!!) }
        }
    }

    private fun fetchReadSchedule(scheduleId: Int, showToast: (String) -> Unit) {
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
                    setRouteInfos(res.busStops.map { BusStopInfoUIFactory.create(it) })
                    _arriveBusStop.update { res.destinationInfo.asBusStop() }
                }
            }.onFailure { showToast(it.message!!) }
        }
    }

    private fun fetchPutSchedule(onSuccess: () -> Unit, showToast: (String) -> Unit) {
        viewModelScope.launch {
            putScheduleUseCase(
                scheduleId = scheduleId!!,
                name = scheduleName.value,
                daysList = dayOfWeeks.value.filter { it.isSelected }
                    .map { "${it.dayOfWeek.value}요일" },
                startTime = startTime.value,
                endTime = endTime.value,
                routeInfos = routeInfos.map { it.asRouteInfo() },
                destinationInfo = arriveBusStop.value.asDestinationInfo(),
                isAlarmOn = isNotify.value
            ).onSuccess {
                onSuccess()
                showToast("스케줄을 수정했습니다.")
                updateWidget()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun putOrPostSchedule(
        onSuccessOfPut: () -> Unit,
        onSuccessOfPost: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        try {
            if (isBiggerStartTime(endTime.value).not()) {
                showToast("종료 시간이 시작 시간보다 늦습니다.")
                return
            }
        } catch (e: Exception) {
            showToast("시간이 입력되지 않았습니다.")
        }
        if (isUpdateSchedule()) {
            fetchPutSchedule(onSuccess = { onSuccessOfPut() }) { showToast(it) }
            return
        }
        fetchPostBusSchedule(onSuccess = { onSuccessOfPost() }) { showToast(it) }
    }

    fun isEqualBusStop(c: String): Boolean {
        return busStop.value.busStop == c
    }

    private fun fetchInsertRecentlySearchBusStop(region: String, search: String) {
        viewModelScope.launch {
            if (recentlySearchBusStopRepository.existsBySearch(search))
                return@launch
            if (recentlySearchBusStopRepository.getCount() == 3) {
                recentlySearchBusStopRepository.deleteOldestSearch()
            }
            recentlySearchBusStopRepository.insert(region, search)
        }
    }

    fun fetchReadAllRecentlySearchBusStop() {
        viewModelScope.launch {
            _recentlySearchBusStop.update { recentlySearchBusStopRepository.realAll() }
        }
    }

    fun fetchDeleteRecentlySearchBusStop(search: String) {
        viewModelScope.launch {
            if (recentlySearchBusStopRepository.delete(search)) {
                _recentlySearchBusStop.update { recentlySearchBusStopRepository.realAll() }
            }
        }
    }

    fun fetchInsertTempSchedule(navigateToScheduleList: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            tempSaveScheduleRepository.insert(
                name = scheduleName.value,
                dayOfWeeks = dayOfWeeks.value.filter { it.isSelected }.map { it.dayOfWeek.value },
                startTime = startTime.value,
                endTime = endTime.value,
                routeInfos = routeInfos.map { it.asRouteInfo() },
                arriveBusStop = arriveBusStop.value,
            )
            withContext(Dispatchers.Main) { navigateToScheduleList() }
        }
    }

    private fun fetchReadTempSchedule() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isExistTempSchedule) {
                val temp = tempSaveScheduleRepository.read()
                _scheduleName.update { temp.name }
                _dayOfWeeks.update {
                    DayOfWeek.entries.map {
                        DayOfWeekUi(
                            dayOfWeek = it,
                            init = temp.days.contains(it.value)
                        )
                    }
                }
                _startTime.update { temp.startTime }
                _endTime.update { temp.endTime }
                _isNotify.update { temp.isAlarmOn }
                _routeInfos.addAll(temp.busStops.map { BusStopInfoUIFactory.create(it) })
                _arriveBusStop.update { temp.destinationInfo.asBusStop() }
                tempSaveScheduleRepository.delete()
            }
        }
    }
}