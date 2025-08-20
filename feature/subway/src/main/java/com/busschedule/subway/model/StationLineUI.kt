package com.busschedule.subway.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class StationLineUI(
    val name: String,
    private val initSelected: Boolean = false
) {
    var isSelected by mutableStateOf(initSelected)

    fun setSelect(s: Boolean) = apply {
        isSelected = s
    }
    override fun toString(): String =
        "name: $name isSelected: $isSelected"
}
