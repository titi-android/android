package com.busschedule.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.busschedule.model.NotifyMessage
import com.busschedule.model.ScheduleNotify


@Entity(tableName = EntityTable.NOTIFY_SCHEDULE)
data class ScheduleNotifyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val scheduleId: String,
    val scheduleName: String,
    val busStopIndex: Int = 0,
    val notifyMessages: List<NotifyMessage> = emptyList()
)

fun ScheduleNotifyEntity.toModel() = ScheduleNotify(
    scheduleId = scheduleId,
    scheduleName = scheduleName,
    notifyIndex = busStopIndex,
    notifyMessages = notifyMessages
)