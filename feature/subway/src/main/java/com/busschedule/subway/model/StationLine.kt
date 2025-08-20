package com.busschedule.subway.model

enum class StationLine(val value: String) {
    LINE_1("1호선"),
    LINE_2("2호선"),
    LINE_3("3호선"),
    LINE_4("4호선"),
    LINE_5("5호선"),
    LINE_6("6호선"),
    LINE_7("7호선"),
    LINE_8("8호선"),
    LINE_9("9호선");

    companion object {
        fun fromValue(lineName: String): String =
            requireNotNull(StationLine.entries.find { it.value == lineName }?.name) { "StationLine not found for value: $lineName" }
    }
}