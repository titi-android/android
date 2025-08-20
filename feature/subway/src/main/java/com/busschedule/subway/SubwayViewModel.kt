package com.busschedule.subway

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.subway.GetSubwayStationLineInfoUseCase
import com.busschedule.subway.model.StationLineUI
import com.busschedule.subway.model.StationUI
import com.busschedule.subway.model.SubwayManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubwayViewModel @Inject constructor(
    private val getSubwayStationLineInfoUseCase: GetSubwayStationLineInfoUseCase,
) : ViewModel() {
    val TAG = this::class.java.simpleName
    var subwayManager = SubwayManager()
    private val _station = MutableStateFlow(emptyList<StationUI>())
    val station: StateFlow<List<StationUI>> = _station.asStateFlow()

    init {
        val dummy = (0 until 15).mapIndexed { index, it ->
            StationUI(id = index, stationNm = "${index}역", lineNum = "2호선")
        }
        _station.update { dummy }
    }

    fun changeStationLineState(stName: String) {
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
                subwayManager.setStationLines(stationLine.toList())
                subwayManager.setInputStationName(inputStationName)
            }.onFailure {
                Log.e(TAG, "error: ${it.message}")
            }
        }
    }

}