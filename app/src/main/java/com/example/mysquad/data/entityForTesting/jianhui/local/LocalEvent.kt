package com.example.mysquad.data.entityForTesting.jianhui.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mysquad.data.entityForTesting.jianhui.Event
import com.example.mysquad.data.entityForTesting.jianhui.EventStatus
import com.example.mysquad.data.entityForTesting.jianhui.EventType
import com.example.mysquad.data.entityForTesting.jianhui.local.LocalUser.user1
import com.example.mysquad.data.entityForTesting.jianhui.local.LocalUser.user2
import com.example.mysquad.data.entityForTesting.jianhui.local.LocalUser.user3
import com.example.mysquad.data.entityForTesting.jianhui.local.LocalUser.user4
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object LocalEvent {
    @RequiresApi(Build.VERSION_CODES.O)
    val event1 = Event(
        eventID = 1,
        eventType = EventType.Running,
        eventTitle = "Morning Run at Carlton Gardens",
        eventDescription = "Join us for a refreshing run around the gardens!",
        eventDate = LocalDate.of(2025, 4, 20),
        eventStartTime = LocalTime.of(7, 30),
        eventEndTime = LocalTime.of(9, 0),
        eventAddress = "Carlton Gardens, Melbourne VIC",
        eventMinimumCapacity = 3,
        eventMaximumCapacity = 10,
        eventCurrentCapacity = 3,
        eventHost = user1,
        eventParticipants = listOf(user2, user3),
        eventApplicant = listOf(user4),
        eventStatus = EventStatus.Upcoming,
        eventCreatedAt = LocalDateTime.now(),
        eventUpdatedAt = LocalDateTime.now()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val event2 = Event(
        eventID = 2,
        eventType = EventType.Yoga,
        eventTitle = "Sunset Yoga at Flagstaff",
        eventDescription = "Wind down with an evening yoga session.",
        eventDate = LocalDate.of(2025, 4, 21),
        eventStartTime = LocalTime.of(18, 0),
        eventEndTime = LocalTime.of(19, 30),
        eventAddress = "Flagstaff Gardens, Melbourne VIC",
        eventMinimumCapacity = 2,
        eventMaximumCapacity = 15,
        eventCurrentCapacity = 2,
        eventHost = user2,
        eventParticipants = listOf(user1),
        eventApplicant = listOf(user3, user4),
        eventStatus = EventStatus.Upcoming,
        eventCreatedAt = LocalDateTime.now(),
        eventUpdatedAt = LocalDateTime.now()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val event3 = Event(
        eventID = 1,
        eventType = EventType.Running,
        eventTitle = "Badminton at Monash Sport",
        eventDescription = "Join us for a refreshing run around the gardens!",
        eventDate = LocalDate.of(2025, 4, 10),
        eventStartTime = LocalTime.of(7, 30),
        eventEndTime = LocalTime.of(9, 0),
        eventAddress = "Carlton Gardens, Melbourne VIC",
        eventMinimumCapacity = 3,
        eventMaximumCapacity = 10,
        eventCurrentCapacity = 3,
        eventHost = user1,
        eventParticipants = listOf(user2, user3),
        eventApplicant = listOf(user4),
        eventStatus = EventStatus.Completed,
        eventCreatedAt = LocalDateTime.now(),
        eventUpdatedAt = LocalDateTime.now()
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val event4 = Event(
        eventID = 2,
        eventType = EventType.Yoga,
        eventTitle = "Swimming section for beginner",
        eventDescription = "Wind down with an evening yoga session.",
        eventDate = LocalDate.of(2025, 4, 21),
        eventStartTime = LocalTime.of(18, 0),
        eventEndTime = LocalTime.of(19, 30),
        eventAddress = "Flagstaff Gardens, Melbourne VIC",
        eventMinimumCapacity = 2,
        eventMaximumCapacity = 15,
        eventCurrentCapacity = 2,
        eventHost = user2,
        eventParticipants = listOf(user3),
        eventApplicant = listOf(user1, user4),
        eventStatus = EventStatus.Upcoming,
        eventCreatedAt = LocalDateTime.now(),
        eventUpdatedAt = LocalDateTime.now()
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val events = listOf(
        event3,
        event1,
        event2,
        event4
    )
}

