package com.busschedule.register

import com.busschedule.domain.repository.RecentlySearchBusStopRepository
import com.busschedule.model.RecentlySearchBusStop

class FakeRecentlySearchBusStopRepository: RecentlySearchBusStopRepository {
    override suspend fun insert(region: String, search: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOldestSearch(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(search: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun realAll(): List<RecentlySearchBusStop> {
        TODO("Not yet implemented")
    }

    override suspend fun existsBySearch(search: String): Boolean {
        TODO("Not yet implemented")
    }
}