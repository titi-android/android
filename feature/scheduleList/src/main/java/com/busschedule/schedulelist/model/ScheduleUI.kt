package com.busschedule.schedulelist.model

import com.busschedule.model.Schedule

data class ScheduleUI(
    val id: Int = 0,
    val scheduleName: String = "",
    val destinationName: String = "",
    val ticketUI: List<TransitTicketUI> = emptyList(),
)

fun Schedule.asStateUI() =
    ScheduleUI(
        id = id,
        scheduleName = name,
        destinationName = destinationName,
        ticketUI = sections.map { it.asTransitTicketUI() }
    )