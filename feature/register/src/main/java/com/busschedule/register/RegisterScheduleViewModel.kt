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
import com.busschedule.domain.repository.TempSaveScheduleRepository
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleUseCase
import com.busschedule.domain.usecase.schedule.ReadScheduleUseCase
import com.busschedule.model.BusRegister
import com.busschedule.model.DayOfWeekUi
import com.busschedule.model.constant.DayOfWeek
import com.busschedule.navigation.Route
import com.busschedule.register.model.ScheduleRegister
import com.busschedule.register.model.TransitCardUI
import com.busschedule.register.model.asTransitCardUI
import com.busschedule.widget.widget.worker.ScheduleWorker
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
class RegisterScheduleViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postScheduleUseCase: PostScheduleUseCase,
//    private val readAllBusStopUseCase: ReadAllBusStopUseCase,
    private val readScheduleUseCase: ReadScheduleUseCase,
    private val putScheduleUseCase: PutScheduleUseCase,
    private val tempSaveScheduleRepository: TempSaveScheduleRepository,
//    private val dispatcher: CoroutineDispatcher = Dispatchers.IO, // 기존 코드
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val scheduleId = savedStateHandle.toRoute<Route.RegisterSchedule>().id
    private val isExistTempSchedule =
        savedStateHandle.toRoute<Route.RegisterSchedule>().isExistTempSchedule

    // 0 이 마지막 카드, 1~n까지 출발, 환승
    private var currentFocusTransitCard = LAST_TRANSIT_CARD_ID

    private val selectDayOfWeek =
        savedStateHandle.toRoute<Route.RegisterSchedule>().dayOfWeek

    private val _scheduleName = MutableStateFlow("")
    val scheduleName: StateFlow<String> = _scheduleName.asStateFlow()

    private val _dayOfWeeks =
        MutableStateFlow(DayOfWeek.entries.map {
            DayOfWeekUi(dayOfWeek = it, init = isEqualDayOfWeek(it, selectDayOfWeek))
        })
    val dayOfWeeks: StateFlow<List<DayOfWeekUi>> = _dayOfWeeks.asStateFlow()

    private val _startTime = MutableStateFlow(START_TIME_EMPTY)
    val startTime: StateFlow<String> = _startTime.asStateFlow()

    private val _endTime = MutableStateFlow(END_TIME_EMPTY)
    val endTime: StateFlow<String> = _endTime.asStateFlow()

    private val _isNotify = MutableStateFlow(false)
    val isNotify: StateFlow<Boolean> = _isNotify.asStateFlow()

    private var _transitCardUIInfos = mutableStateListOf<TransitCardUI>()
    val transitCardUIInfos: List<TransitCardUI> = _transitCardUIInfos

    private val _lastTransitCardUIInfos = MutableStateFlow<TransitCardUI>(TransitCardUI.Bus())
    val lastTransitCardUIInfos: StateFlow<TransitCardUI> = _lastTransitCardUIInfos.asStateFlow()

    val registerBusScheduleUiState =
        combine(scheduleName, dayOfWeeks, startTime, endTime, isNotify)
        { scheduleName, dayOfWeeks, startTime, endTime, isNotify ->
            ScheduleRegister(
                name = scheduleName,
                dayOfWeeks = dayOfWeeks,
                startTime = startTime,
                endTime = endTime,
                isNotify = isNotify
            )
        }


    init {
        if (isExistTempSchedule) /*fetchReadTempSchedule() */
        else if (isUpdateSchedule()) viewModelScope.launch {
            fetchReadSchedule(scheduleId!!) { showToastMsg(it) }
        }
    }


    fun isUpdateSchedule(): Boolean = scheduleId != null

//    fun isRouteInfoNotEmpty():Boolean {
//        if (transitTypeInfos.isEmpty()) return false
//        return transitTypeInfos.first().isNotBlank()
//    }

    fun setCurrentFocusTransitCard(id: Int) {
        currentFocusTransitCard = id
    }

    fun getCurrentFocusTransitCard() = currentFocusTransitCard

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

    fun addSubwayInfo(data: List<String>) {
        val subway = TransitCardUI.Subway(
            content1 = data[0],
            content2 = "${data[1]}, ${data[2]}",
            subwayDirection = data[3],
            upDownDir = data[4],
        )
//        Log.e("daeyoung", "wubway: $subway")
        if (subway.isEmpty()) return

        if (currentFocusTransitCard == LAST_TRANSIT_CARD_ID) {
            _lastTransitCardUIInfos.update { subway }
            return
        }
        if (transitCardUIInfos.size == currentFocusTransitCard) {
            _transitCardUIInfos.add(subway)
            return
        }
        _transitCardUIInfos[currentFocusTransitCard] = subway
    }

    fun addBusInfo(data: BusRegister) {
        val bus = TransitCardUI.Bus(
            content1 = data.regionName,
            content2 = data.busStopName,
            nodeId = data.nodeId,
            buses = data.busInfos
        )
        if (bus.isEmpty()) return

        Log.i(
            "daeyoung",
            "currentFocusTransitCard: $currentFocusTransitCard\ntransitCardUIInfos.size: ${transitCardUIInfos.size} "
        )
        if (currentFocusTransitCard == LAST_TRANSIT_CARD_ID) {
            _lastTransitCardUIInfos.update { bus }
            return
        }
        if (transitCardUIInfos.size == currentFocusTransitCard) {
            _transitCardUIInfos.add(bus)
            return
        }
        _transitCardUIInfos[currentFocusTransitCard] = bus
    }

    fun removeTransitCard(id: Int) {
        _transitCardUIInfos.removeAt(id)
    }

//    fun addBusStopInfoUI() {
//        _transitTypeInfos.add(BusStopInfoUIFactory.create())
//    }

//    fun removeBusStopInfoUI(id: Int) {
//        _transitTypeInfos.removeIf { it.compareID(id) }
//    }


//    fun addBusStopInSelectBusStopInfo(id: Int, popBackStack: () -> Unit) {
//        val bus = busStop.value
//        val index = _transitTypeInfos.indexOfFirst { it.compareID(id) }
//        Log.d("daeyoung", "index: $index")
//        _transitTypeInfos[index] = _transitTypeInfos[index].copy(
//            region = bus.region,
//            busStop = bus.busStop,
//            nodeId = bus.nodeId,
//            busesInit = bus.buses.filter { it.isSelected }
//                .map { BusInfo(name = it.name, type = it.type.name) }
//        )
//        cityOfRegion.value.unAllSelect()
//        _busStopInput.update { "" }
//        _busStop.update { SelectedBusUI() }
//        popBackStack()
//    }

    private fun showToastMsg(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setRouteInfos(list: List<TransitCardUI>) {
        if (transitCardUIInfos.isNotEmpty()) _transitCardUIInfos.clear()
        _transitCardUIInfos.addAll(list)
    }

    private fun updateWidget() {
        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<ScheduleWorker>().build()
        )
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
                routeInfos = transitCardUIInfos.map { it.asRouteInfo() },
                destinationInfo = lastTransitCardUIInfos.value.asDestinationInfo(),
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
                    setRouteInfos(res.routeInfos.map { it.asTransitCardUI() })
                    _lastTransitCardUIInfos.update { res.destinationInfo.asTransitCardUI() }
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
                routeInfos = transitCardUIInfos.map { it.asRouteInfo() },
                destinationInfo = lastTransitCardUIInfos.value.asDestinationInfo(),
                isAlarmOn = isNotify.value
            ).onSuccess {
                onSuccess()
                showToast("스케줄을 수정했습니다.")
                updateWidget()
            }.onFailure { showToast(it.message!!) }
        }
    }


    fun putOrPostSchedule(
        navigateToScheduleList: () -> Unit = {},
        showToast: (String) -> Unit,
    ) {
        try {
            if (startTime.value == START_TIME_EMPTY) {
                showToast("시작 시간이 입력되지 않았습니다.")
                return
            }
            if (endTime.value == END_TIME_EMPTY) {
                showToast("종료 시간이 입력되지 않았습니다.")
                return
            }
            if (isBiggerStartTime(endTime.value).not()) {
                showToast("종료 시간이 시작 시간보다 늦습니다.")
                return
            }
            if (transitCardUIInfos.first().isEmpty()) {
                showToast("춥발지가 입력되지 않았습니다.")
                return
            }
            if (lastTransitCardUIInfos.value.isEmpty()) {
                showToast("도착지가 입력되지 않았습니다.")
                return
            }
            if (isUpdateSchedule()) {
                fetchPutSchedule(onSuccess = { navigateToScheduleList() }) { showToast(it) }
                return
            }
            fetchPostBusSchedule(onSuccess = { navigateToScheduleList() }) { showToast(it) }
        } catch (e: Exception) {
            showToast("주어진 항목을 모두 입력해주세요.")
        }
    }


    /*
    fun fetchInsertTempSchedule(navigateToScheduleList: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            tempSaveScheduleRepository.insert(
                name = scheduleName.value,
                dayOfWeeks = dayOfWeeks.value.filter { it.isSelected }.map { it.dayOfWeek.value },
                startTime = startTime.value,
                endTime = endTime.value,
                routeInfos = transitTypeInfos.map { it.asRouteInfo() },
                arriveBusStop = arriveBusStop.value,
            )
            withContext(Dispatchers.Main) { navigateToScheduleList() }
        }
    }

     */

    /*
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
                _transitTypeInfos.addAll(temp.busStops.map { BusStopInfoUIFactory.create(it) })
                _arriveBusStop.update { temp.destinationInfo.asBusStop() }
                tempSaveScheduleRepository.delete()
            }
        }
    }
     */

    companion object {
        private const val START_TIME_EMPTY = "시작 시간"
        private const val END_TIME_EMPTY = "종료 시간"

        const val LAST_TRANSIT_CARD_ID = 5
        const val START_NOT_INIT_TRANSIT_CARD_ID = 0
    }
}