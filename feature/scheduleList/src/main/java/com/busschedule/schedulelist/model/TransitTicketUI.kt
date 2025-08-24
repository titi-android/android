package com.busschedule.schedulelist.model

import com.busschedule.model.TransitInfo
import com.busschedule.model.constant.TransitConst

sealed interface TransitTicketUI {
    fun getStartingPoint(): String

    /*
    fun getArriveTime(): String

    fun getRemainingStopsCount(): String

    fun getArriveBusOrStationNum(): String
     */

    fun getDestinationTransits(): List<DestinationTransitInfo>

    class BusTicketUI(
        val busStopName: String = "",
        val destinationTransitList: List<DestinationTransitInfo> = emptyList(),
//        val arrtime: Int = 0,
//        val routeno: String,
//        val arrprevstationcnt: Int,
    ) : TransitTicketUI {
//        override fun getArriveBusOrStationNum(): String = routeno

        override fun getStartingPoint(): String = busStopName

//        override fun getArriveTime(): String = arrtime.toFormatKrTime()

//        override fun getRemainingStopsCount(): String = "${arrprevstationcnt}정거장"

        override fun getDestinationTransits(): List<DestinationTransitInfo> = destinationTransitList
    }

    /**
     * @param statnNm: 도착 예정 정보를 받을 역의 이름입니다.
     * @param lineName: 지하철 호선에 대한 이름입니다. 예) 2호선
     * @param barvlDt: 열차의 현재 도착 예정 시간입니다. 단위는 초이며, "0"은 열차가 곧 도착하거나 이미 도착했음을 의미합니다.
     * @param arvlMsg2: 도착 정보 메시지입니다. 열차가 몇 번째 전역에 있는지 또는 "종각 도착"과 같은 구체적인 상태를 텍스트로 알려줍니다.
     */
    class SubwayTicketUI(
        val stationName: String,
        val destinationTransitList: List<DestinationTransitInfo> = emptyList(),
//        val lineName: String,
//        val barvlDt: Int = 0,
//        val arvlMsg2: String,
    ) : TransitTicketUI {
//        override fun getArriveBusOrStationNum(): String = lineName

        override fun getStartingPoint(): String = stationName

//        override fun getArriveTime(): String = barvlDt.toFormatKrTime()

        //        override fun getRemainingStopsCount(): String = arvlMsg2
        override fun getDestinationTransits(): List<DestinationTransitInfo> = destinationTransitList
    }
}

fun TransitInfo.asTransitTicketUI(): TransitTicketUI =
    when (this.type) {
        TransitConst.BUS.name -> {
            TransitTicketUI.BusTicketUI(
                busStopName = busStop!!.busStopName,
                destinationTransitList = busStop?.busArrivals?.map { arrival ->
                    DestinationTransitInfo(
                        arrtime = arrival.arrtime,
                        routeno = arrival.routeno,
                        routeType = arrival.routetp,
                        arrprevstationcnt = "${arrival.arrprevstationcnt}정거장"
                    )
                } ?: emptyList()
            )
        }

        TransitConst.SUBWAY.name -> {
            TransitTicketUI.SubwayTicketUI(
                stationName = subway!!.stationName,
                destinationTransitList = subway?.arrivals?.map { arrival ->
                    DestinationTransitInfo(
                        arrtime = arrival.barvlDt.toInt(),
                        routeno = subway!!.lineName,
                        routeType = subway!!.lineName,
                        arrprevstationcnt = arrival.arvlMsg2
                    )
                } ?: emptyList()
            )
        }

        // TODO: 임시...
        else -> {
            TransitTicketUI.BusTicketUI(
                busStopName = busStop!!.busStopName,
                destinationTransitList = busStop?.busArrivals?.map { arrival ->
                    DestinationTransitInfo(
                        arrtime = arrival.arrtime,
                        routeno = arrival.routeno,
                        routeType = arrival.routetp,
                        arrprevstationcnt = "${arrival.arrprevstationcnt}정거장"
                    )
                } ?: emptyList()
            )
        }
    }
