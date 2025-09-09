package com.busschedule.register.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.busschedule.model.BusInfo
import com.busschedule.model.BusStopSection
import com.busschedule.model.DestinationInfo
import com.busschedule.model.RouteInfo
import com.busschedule.model.SubwaySection
import com.busschedule.model.constant.TransitConst
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.ImageBus
import core.designsystem.svg.myiconpack.ImageSubway

sealed class TransitCardUI(
    open val type: TransitConst = TransitConst.BUS,
    open val title1: String = "",
    open val title2: String = "",
    open val title3: String = "",
    open val icon: ImageVector = MyIconPack.ImageBus,
    open val content1: String = "",
    open val content2: String = "",
    open val subwayDirection: String = "",
    open val buses: List<BusInfo> = emptyList(),
) {
    abstract fun isEmpty(): Boolean

    data class Bus(
        override val title1: String = "지역",
        override val title2: String = "버스 정류장",
        override val title3: String = "버스 번호",
        override val icon: ImageVector = MyIconPack.ImageBus,
        override val content1: String = "", // 지역이름, 서울
        override val content2: String = "", // 정류 정류장 이름
        val nodeId: String = "",
        override val buses: List<BusInfo> = emptyList(),
    ) : TransitCardUI() {
        override fun isEmpty(): Boolean =
//            this.buses.isEmpty() || content2.isEmpty() || content1.isEmpty()
            content2.isEmpty() || content1.isEmpty()

    }

    data class Subway(
        override val title1: String = "지역",
        override val title2: String = "지하철 역",
        override val title3: String = "지하철 방향",
        override val icon: ImageVector = MyIconPack.ImageSubway,
        override val content1: String, // 서울 특별시
        override val content2: String, // 1호선 종각역
        override val subwayDirection: String, // 신설동역 방향
        val upDownDir: String = "UP",
    ) : TransitCardUI() {
        override fun isEmpty(): Boolean =
//            this.content1.isEmpty() || this.content2.isEmpty() || this.subwayDirection.isEmpty()
            this.content2.isEmpty()
    }

    fun asRouteInfo(): RouteInfo =
        when (this) {
            is Bus -> {
                RouteInfo(
                    type = TransitConst.BUS.name,
                    busStopSection = BusStopSection(
                        regionName = content1,
                        busStopName = content2,
                        nodeId = nodeId,
                        busList = buses
                    ),
                    subwaySection = null
                )
            }

            is Subway -> {
                val list = content2.split(", ")
                RouteInfo(
                    type = TransitConst.SUBWAY.name,
                    busStopSection = null,
                    subwaySection = SubwaySection(
                        regionName = content1,
                        lineName = list[0],
                        stationName = list[1],
                        dirName = subwayDirection,
                        dir = upDownDir
                    )
                )
            }
        }

    fun asDestinationInfo(): DestinationInfo = when (this) {
        is Bus -> {
            DestinationInfo(
                type = TransitConst.BUS.name,
                regionName = content1,
                desName = content2,
                lineName = "",
                dir = ""
            )
        }

        is Subway -> {
            val list = content2.split(", ")
            DestinationInfo(
                type = TransitConst.SUBWAY.name,
                regionName = content1,
                desName = list[1],
                lineName = list[0],
                dir = upDownDir
            )
        }
    }
}

fun RouteInfo.asTransitCardUI(): TransitCardUI {
    return when (type) {
        TransitConst.BUS.name -> {
            val busStopSection = busStopSection!!
            TransitCardUI.Bus(
                content1 = busStopSection.regionName,
                content2 = busStopSection.busStopName,
                nodeId = busStopSection.nodeId,
                buses = busStopSection.busList
            )
        }

        TransitConst.SUBWAY.name -> {
            val subwaySection = subwaySection!!
            TransitCardUI.Subway(
                content1 = subwaySection.regionName,
                content2 = "${subwaySection.lineName} ${subwaySection.stationName}",
                subwayDirection = "",
                upDownDir = subwaySection.dir
            )
        }

        else -> {
            val busStopSection = busStopSection!!
            TransitCardUI.Bus(
                content1 = busStopSection.regionName,
                content2 = busStopSection.busStopName,
                nodeId = busStopSection.nodeId,
                buses = busStopSection.busList
            )
        }
    }
}

fun DestinationInfo.asTransitCardUI(): TransitCardUI = when (this.type) {
    TransitConst.BUS.name -> {
        TransitCardUI.Bus(
            content1 = this.regionName,
            content2 = this.desName,
            nodeId = "",
            buses = emptyList()
        )
    }

    TransitConst.SUBWAY.name -> {
        TransitCardUI.Subway(
            content1 = this.regionName,
            content2 = "${this.lineName} ${this.desName}",
            subwayDirection = "",
            upDownDir = ""
        )
    }
    else -> {
        TransitCardUI.Bus(
            content1 = this.regionName,
            content2 = this.desName,
            nodeId = "",
            buses = emptyList()
        )
    }
}