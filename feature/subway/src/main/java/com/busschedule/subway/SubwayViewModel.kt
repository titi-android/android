package com.busschedule.subway

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.subway.GetSubwayStationLineInfoUseCase
import com.busschedule.domain.usecase.subway.GetSubwayStationUseCase
import com.busschedule.subway.model.StationLine
import com.busschedule.subway.model.StationLineUI
import com.busschedule.subway.model.SubwayManager
import com.busschedule.subway.model.SubwayStationUI
import com.busschedule.subway.model.asState
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
    private val getSubwayStationUseCase: GetSubwayStationUseCase
) : ViewModel() {
    val TAG = this::class.java.simpleName
    var subwayManager = SubwayManager()
    private val _subwayStations = MutableStateFlow(emptyList<SubwayStationUI>())
    val subwayStations: StateFlow<List<SubwayStationUI>> = _subwayStations.asStateFlow()

    private fun changeStationLineState(stName: String) {
        subwayManager.changeStationLineState(stName)
    }

    fun fetchGetSubwayStationLineInfo(stName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSubwayStationLineInfoUseCase(stName).onSuccess { result ->
                val inputStationName = result.first().stationName
                val stationLine = mutableSetOf<StationLineUI>()
                result.forEach { subwayStationLineInfo ->
                    subwayStationLineInfo.lines.forEach {
                        stationLine.add(
                            StationLineUI(name = it, initSelected = false)
                        )
                    }
                }
                val sortedStationLine = stationLine.toList().sortedBy { it.name }
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
                    launch { _subwayStations.update { result.mapIndexed { idx, station -> station.asState(idx) } } }
                }
            }.onFailure { Log.e(TAG, "fetchGetSubwayStation() called, error: ${it.message}") }
        }
    }

}