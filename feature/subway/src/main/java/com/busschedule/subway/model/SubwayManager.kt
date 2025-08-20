package com.busschedule.subway.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SubwayManager {
    private val _stationLines = MutableStateFlow(emptyList<StationLineUI>())
    val stationLines: StateFlow<List<StationLineUI>> = _stationLines.asStateFlow()

    private var inputStationName = ""

    fun setStationLines(newLines: List<StationLineUI>) {
        val sortedLines = newLines.sortedBy { it.name }
            .mapIndexed { index, it ->
                it.apply { it.setSelect(index == 0) }
            }
        _stationLines.update { sortedLines }
    }

    fun setInputStationName(input: String) {
        inputStationName = input
    }

    fun changeStationLineState(stName: String) {
        _stationLines.update { lines ->
            lines.map {
                if (it.name == stName) it.setSelect(true)
                else it.setSelect(false)
            }
        }
    }

}