package com.busschedule.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.busschedule.model.BusRegister
import com.busschedule.model.BusStop
import com.busschedule.model.ScheduleRegister

@Entity(tableName = EntityTable.REGISTER_SCHEDULE)
data class ScheduleRegisterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val dayOfWeeks: List<String> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val isNotify: Boolean = false,
    val busRegisters: List<BusRegister> = emptyList(),
    val arriveBusStop: BusStop = BusStop(),
)

fun ScheduleRegisterEntity.toModel() = ScheduleRegister(
    id = id,
    name = name,
    days = dayOfWeeks,
    startTime = startTime,
    endTime = endTime,
    busStops = busRegisters,
//    destinationInfo = arriveBusStop.asDestinationInfo(),
    isAlarmOn = isNotify,
)