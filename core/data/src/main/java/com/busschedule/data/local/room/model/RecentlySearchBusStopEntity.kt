package com.busschedule.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.busschedule.model.RecentlySearchBusStop

@Entity(tableName = EntityTable.RECENTLY_SEARCH_BUSSTOP)
data class RecentlySearchBusStopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val search: String = "",
)


fun RecentlySearchBusStopEntity.toModel() = RecentlySearchBusStop(
    id = id,
    search = search
)