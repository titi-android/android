package com.busschedule.register.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.busschedule.model.BusInfo
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.ImageBus
import core.designsystem.svg.myiconpack.ImageSubway

sealed class TransitType(
    open val title1: String = "",
    open val title2: String = "",
    open val title3: String = "",
    open val icon: ImageVector = MyIconPack.ImageBus,
    val content1: String,
    val content2: String,
) {

    data class Bus(
        override val title1: String = "지역",
        override val title2: String = "버스 정류장",
        override val title3: String = "버스 번호",
        override val icon: ImageVector = MyIconPack.ImageBus,
        val region: String,
        val busStopName: String,
        val nodeId: String = "",
        val buses: List<BusInfo> = emptyList(),
    ) : TransitType(content1 =  region, content2 = busStopName) {
        companion object {
            fun empty() = Bus(
                region = "",
                busStopName = "",
            )
        }
    }

    data class Subway(
        override val title1: String = "호선",
        override val title2: String = "지하철 역",
        override val title3: String = "지하철 방향",
        override val icon: ImageVector = MyIconPack.ImageSubway,
        val line: String,
        val subwayStationName: String,
        val direction: String,
    ) : TransitType (content1 = line, content2 = subwayStationName) {
        companion object {
            fun empty() = Subway(
                line = "",
                subwayStationName = "",
                direction = "",
            )
        }
    }
}