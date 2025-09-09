package com.busschedule.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.repository.RecentlySearchBusStopRepository
import com.busschedule.domain.usecase.bus.ReadAllBusOfBusStopUseCase
import com.busschedule.domain.usecase.busstop.ReadAllBusStopUseCase
import com.busschedule.model.RecentlySearchBusStop
import com.busschedule.model.constant.BusType
import com.busschedule.model.constant.TransitConst
import com.busschedule.register.model.AddBusDialogUiState
import com.busschedule.register.model.Bus
import com.busschedule.register.model.CityOfRegion
import com.busschedule.register.model.KakaoMapObject
import com.busschedule.register.model.SelectBusStopUiState
import com.busschedule.register.model.SelectRegionUiState
import com.busschedule.register.model.SelectedBusUI
import com.kakao.vectormap.KakaoMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val readAllBusStopUseCase: ReadAllBusStopUseCase,
    private val readAllBusOfBusStopUseCase: ReadAllBusOfBusStopUseCase,
    private val recentlySearchBusStopRepository: RecentlySearchBusStopRepository,
) : ViewModel() {

    lateinit var kakaoMap: KakaoMapObject

    // 지역 선택하는 화면에 필요한 상태 값
    private val _regionInput = MutableStateFlow("")
    val regionInput: StateFlow<String> = _regionInput.asStateFlow()

    // 지역 선택하는 화면에 필요한 상태 값
    private val _cityOfRegion = MutableStateFlow(CityOfRegion())
    val cityOfRegion: StateFlow<CityOfRegion> = _cityOfRegion.asStateFlow()

    private val _busStopInput = MutableStateFlow("")
    val busStopInput: StateFlow<String> = _busStopInput.asStateFlow()

    private val _busOfBusStop = MutableStateFlow(SelectedBusUI())
    val busOfBusStop: StateFlow<SelectedBusUI> = _busOfBusStop.asStateFlow()

    private val _recentlySearchBusStop = MutableStateFlow(emptyList<RecentlySearchBusStop>())
    val recentlySearchBusStop: StateFlow<List<RecentlySearchBusStop>> =
        _recentlySearchBusStop.asStateFlow()

    private val _addBusInput = MutableStateFlow("")
    val addBusInput: StateFlow<String> = _addBusInput.asStateFlow()

    private val _addBus = MutableStateFlow(emptyList<Bus>())
    val addBus: StateFlow<List<Bus>> = _addBus.asStateFlow()

    val addBusDialogUiState = combine(addBusInput, addBus) { input, addBus ->
        AddBusDialogUiState(input = input, bus = addBus)
    }

    val selectRegionUiState = combine(regionInput, cityOfRegion) { input, cityOfRegion ->
        SelectRegionUiState(
            input = input,
            region = cityOfRegion
        )
    }

    val selectBusStopUiState = combine(busStopInput, recentlySearchBusStop) { input, search ->
        SelectBusStopUiState(input = input, recentlySearchBusStop = search)
    }

    fun updateRegionInput(input: String) {
        _regionInput.update { input }
    }

    fun updateBusStopInput(input: String) {
        _busStopInput.update { input }
    }

    fun updateBusStop(region: String, busStopName: String, nodeId: String) {
        _busOfBusStop.update { it.copy(region = region, busStop = busStopName, nodeId = nodeId) }
    }

    fun updateAddBusInput(input: String) {
        _addBusInput.update { input }
    }

    fun initKakaoMap(map: KakaoMap): KakaoMapObject {
        kakaoMap = KakaoMapObject(map)
        return kakaoMap
    }

    fun isEqualBusStop(c: String): Boolean {
        return busOfBusStop.value.busStop == c
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
        _busOfBusStop.update { busOfBusStop.value.copy(buses = it.buses + addBus.value.filter { addB -> addB.isSelected }) }
        _addBus.update { emptyList() }
    }

    private fun getSelectBusStopInfo(): Map<String, String> = mapOf(
        "transitType" to TransitConst.BUS.name,
        "regionName" to busOfBusStop.value.region,
        "busStopName" to busOfBusStop.value.busStop,
        "nodeId" to busOfBusStop.value.nodeId,
    )

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
        busStopNodeId: String,
        changeLoadingState: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch {
            readAllBusStopUseCase(
                cityName = region,
                nodeId = busStopNodeId
            ).onSuccess { busStop ->
                if (busStop.isNotEmpty()) {
                    kakaoMap.removeAndAddLabel(
                        icon = com.busschedule.designsystem.R.drawable.image_busstop_label,
                        labels = busStop,
                        region = region
                    )
                    println("labels:${kakaoMap.getLabels()}")
                    fetchInsertRecentlySearchBusStop(region, busStopNodeId)
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

    // 버스 정류장 클릭시 해당 버스 정류장을 경유하는 모든 버스 조회
    fun fetchReadAllBusOfBusStop(
        id: Int,
        busStopName: String,
        nodeId: String,
        showBottomSheet: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        viewModelScope.launch {
            readAllBusOfBusStopUseCase(
                cityName = kakaoMap.region,
                busStopId = nodeId
            ).onSuccess { busInfos ->
                _busOfBusStop.update {
                    SelectedBusUI(
                        region = kakaoMap.region,
                        busStop = busStopName,
                        nodeId = nodeId,
                        buses = busInfos.map { bus ->
                            Bus(
                                name = bus.name,
                                type = BusType.find(bus.type),
                                nodeId = nodeId,
//                                selectedInit = routeInfos?.getBuses()
//                                    ?.any { it.name == bus.name && it.type == bus.type && routeInfos.nodeId == nodeId }
//                                    ?: false
                                selectedInit = false
                            )
                        }
                    )
                }
                Log.i("daeyoung", "_busOfBusStop: ${_busOfBusStop.value}")
                showBottomSheet()
            }.onFailure { showToast(it.message!!) }
        }
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

}