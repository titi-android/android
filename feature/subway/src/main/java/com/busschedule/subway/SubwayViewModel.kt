package com.busschedule.subway

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.subway.GetSubwayStationLineInfoUseCase
import com.busschedule.domain.usecase.subway.GetSubwayStationUseCase
import com.busschedule.model.constant.TransitConst
import com.busschedule.subway.model.StationLine
import com.busschedule.subway.model.StationLineUI
import com.busschedule.subway.model.SubwayManager
import com.busschedule.subway.model.SubwayStationUI
import com.busschedule.subway.model.asState
import com.busschedule.subway.ui.StationDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SubwayViewModel @Inject constructor(
    private val getSubwayStationLineInfoUseCase: GetSubwayStationLineInfoUseCase,
    private val getSubwayStationUseCase: GetSubwayStationUseCase,
) : ViewModel() {
    val TAG = this::class.java.simpleName
    var subwayManager = SubwayManager()
    private val _subwayStations = MutableStateFlow(emptyList<SubwayStationUI>())
    val subwayStations: StateFlow<List<SubwayStationUI>> = _subwayStations.asStateFlow()

    private val _selectStations =
        MutableStateFlow<Pair<SubwayStationUI?, SubwayStationUI?>>(null to null)
    val selectStations: StateFlow<Pair<SubwayStationUI?, SubwayStationUI?>> =
        _selectStations.asStateFlow()

    private fun changeStationLineState(stName: String) {
        subwayManager.changeStationLineState(stName)
    }

    fun setSelectStation(id: Int) {
        if (selectStations.value.first?.id == id) return

        val (f, s) = if (selectStations.value.first != null && selectStations.value.second != null)
            null to null
        else if (selectStations.value.first == null) subwayStations.value[id] to null
        else selectStations.value.first to subwayStations.value[id]
        _selectStations.update { f to s }
    }

    fun getSubwayDirection(): StationDirection = if (selectStations.value.first?.id == null || selectStations.value.second == null) StationDirection.NONE
        else if (selectStations.value.first!!.id > selectStations.value.second!!.id) StationDirection.UP
        else StationDirection.DOWN

    fun getSelectSubwayStationTotalInfo(): Map<String, String> = mapOf(
        "transitType" to TransitConst.SUBWAY.name,
        "regionName" to "서울",
        "lineName" to subwayManager.getCurrentSelectStationLine(),
        "stationName" to selectStations.value.first!!.stationNm,
        "dir" to "${selectStations.value.second!!.stationNm} 방향",
        "upDownDir" to getSubwayDirection().name
    )


    fun fetchGetSubwayStationLineInfo(stName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSubwayStationLineInfoUseCase(stName).onSuccess { result ->
                Log.d("daeyoung", "fetchGetSubwayStationLineInfo() called: $result")
                val inputStationName = result.first().stationName
                val stationLineSet = mutableSetOf<StationLineUI>()
                result.forEach { subwayStationLineInfo ->
                    subwayStationLineInfo.lines
                        .filter { StationLine.isExistStationLine(it) }
                        .forEach {
                            stationLineSet.add(
                                StationLineUI(
                                    name = it,
                                    initSelected = false
                                )
                            )
                        }
                }
                val sortedStationLine = stationLineSet.toList().sortedBy { it.name }
                fetchGetSubwayStation(sortedStationLine.first().name)
                subwayManager.setStationLines(sortedStationLine)
                subwayManager.setInputStationName(inputStationName)

            }.onFailure {}
        }
    }

    fun fetchGetSubwayStation(lineName: String) {
        // 1호선 -> LINE_1 로 매핑
        val mappingLineName = StationLine.fromValue(lineName)
        viewModelScope.launch(Dispatchers.IO) {
            getSubwayStationUseCase(mappingLineName).onSuccess { result ->
                withContext(Dispatchers.Main) {
                    launch { changeStationLineState(lineName) }
                    launch {
                        _subwayStations.update {
                            result.mapIndexed { idx, station -> station.asState(idx) }
                        }
                    }
                }
            }.onFailure { Log.e(TAG, "fetchGetSubwayStation() called, error: ${it.message}") }
        }
    }

}