package com.example.mysquad.data.localRoom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mysquad.data.localRoom.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("SELECT * FROM events ORDER BY eventDate ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE eventId = :id")
    suspend fun getEventById(id: String): EventEntity?

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("DELETE FROM events")
    suspend fun clearAllEvents()

    @Query(
        """
        SELECT * FROM events
        WHERE eventDate BETWEEN :startDate AND :endDate
        ORDER BY eventDate DESC
        """
    )
    fun getAllEventsInDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<EventEntity>>
}