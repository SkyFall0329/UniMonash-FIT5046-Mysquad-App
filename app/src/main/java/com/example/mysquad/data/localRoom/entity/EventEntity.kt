package com.example.mysquad.data.localRoom.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val eventId: String = UUID.randomUUID().toString(),
    val eventType: String = "",
    val eventDate: Long = 0L,
    val eventTitle: String = "",
    val eventDescription: String = "",
    val eventAddress: String = "",
    val eventCoordinates: List<Double> = emptyList(),
    val eventStartTime: String = "",
    val eventEndTime: String = "",
    val eventHostUserId: String = "",
    val eventJoinList: List<String> = emptyList(), //already joined
    var eventPendingList: List<String> = emptyList()//applicant
)