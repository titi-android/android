package com.busschedule.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.busschedule.data.local.room.model.EntityTable
import com.busschedule.data.local.room.model.RecentlySearchBusStopEntity

@Dao
interface RecentlySearchBusStopDao {
    @Insert
    suspend fun insert(recentlySearchBusStop: RecentlySearchBusStopEntity)

    @Query("""
    DELETE FROM ${EntityTable.RECENTLY_SEARCH_BUSSTOP} 
    WHERE createdAt = (SELECT createdAt FROM ${EntityTable.RECENTLY_SEARCH_BUSSTOP} ORDER BY createdAt ASC LIMIT 1)
    """)
    suspend fun deleteOldestSearch()

    @Query("DELETE FROM ${EntityTable.RECENTLY_SEARCH_BUSSTOP} WHERE search = :search")
    suspend fun delete(search: String)

    @Query("SELECT COUNT(*) FROM ${EntityTable.RECENTLY_SEARCH_BUSSTOP}")
    suspend fun getCount(): Int

    @Query("SELECT * FROM ${EntityTable.RECENTLY_SEARCH_BUSSTOP} ORDER BY createdAt DESC")
    suspend fun realAll(): List<RecentlySearchBusStopEntity>

    @Query("""
    SELECT EXISTS(
        SELECT 1 FROM ${EntityTable.RECENTLY_SEARCH_BUSSTOP} 
        WHERE search = :search
    )
""")
    suspend fun existsBySearch(search: String): Boolean
}