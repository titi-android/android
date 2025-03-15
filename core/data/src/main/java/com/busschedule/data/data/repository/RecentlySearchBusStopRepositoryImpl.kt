package com.busschedule.data.data.repository

import com.busschedule.data.local.room.dao.RecentlySearchBusStopDao
import com.busschedule.data.local.room.model.RecentlySearchBusStopEntity
import com.busschedule.data.local.room.model.toModel
import com.busschedule.domain.repository.RecentlySearchBusStopRepository
import com.busschedule.model.RecentlySearchBusStop
import javax.inject.Inject

class RecentlySearchBusStopRepositoryImpl @Inject constructor(
    private val recentlySearchBusStopDao: RecentlySearchBusStopDao
) : RecentlySearchBusStopRepository {
    override suspend fun insert(region: String, search: String) {
        recentlySearchBusStopDao.insert(RecentlySearchBusStopEntity(region = region, search = search))
    }

    override suspend fun deleteOldestSearch(): Boolean = try {
        recentlySearchBusStopDao.deleteOldestSearch()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun delete(search: String): Boolean = try {
        recentlySearchBusStopDao.delete(search)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun getCount(): Int = recentlySearchBusStopDao.getCount()

    override suspend fun realAll(): List<RecentlySearchBusStop> =
        recentlySearchBusStopDao.realAll().map { it.toModel() }

    override suspend fun existsBySearch(search: String):Boolean =
        recentlySearchBusStopDao.existsBySearch(search)

}