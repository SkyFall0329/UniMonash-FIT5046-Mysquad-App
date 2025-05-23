package com.example.mysquad.data.localRoom.dao

import android.R.string
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mysquad.data.localRoom.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Query("SELECT * FROM events ORDER BY eventDate ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE eventId = :id")
    fun getEventById(id: String): Flow<EventEntity?>

    @Query("SELECT * FROM events WHERE eventId = :id")
    fun eventById(id: String): EventEntity?

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

    @Query("""SELECT * FROM events 
        WHERE (eventHostUserId = :uid) or 
        (eventJoinList LIKE '%' || :uid || '%')
        ORDER by eventDate ASC""")
    fun getHostedBy(uid:String):Flow<List<EventEntity>>

    @Query("""SELECT * FROM events 
        WHERE (eventJoinList LIKE '%' || :uid || '%')
        ORDER by eventDate ASC""")
    fun getJoinedBy(uid:String):Flow<List<EventEntity>>

    @Query("""
    SELECT * FROM events
    WHERE ',' || eventPendingList || ',' LIKE '%,' || :uid || ',%'
    ORDER BY eventDate ASC
""")
    fun getPending(uid: String): Flow<List<EventEntity>>

    @Query("DELETE FROM events WHERE eventId = :id")
    suspend fun deleteEventById(id: String)
}