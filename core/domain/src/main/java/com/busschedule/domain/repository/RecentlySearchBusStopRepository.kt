package com.busschedule.domain.repository

import com.busschedule.model.RecentlySearchBusStop

interface RecentlySearchBusStopRepository {
    suspend fun insert(search: String)

    suspend fun deleteOldestSearch(): Boolean

    suspend fun delete(search: String): Boolean

    suspend fun getCount(): Int

    suspend fun realAll(): List<RecentlySearchBusStop>

    suspend fun existsBySearch(search: String):Boolean

}