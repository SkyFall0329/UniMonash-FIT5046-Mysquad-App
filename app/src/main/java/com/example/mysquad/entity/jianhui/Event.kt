package com.example.mysquad.entity.jianhui

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Event (
    val eventID: Int,  // unique ID of an Event
    val eventType: EventType,    // the sport type of the event
    val eventTitle: String,   // the title of the event
    val eventDescription: String, // description of the event where host input
    val eventDate: LocalDate,        // Event Date of the event, date need to be able to compatible with Date Picker
    val eventStartTime: LocalTime,   // Start time of the event need to be compatible with Google Calender
    val eventEndTime: LocalTime,     // End time of the event need to be compatible with Google Calender
    val eventAddress: String,     // Address of the event will host or meet point, need to be compatible with Google Map or Mapbox
    val eventMinimumCapacity: Int, // Minimum number of participants required to start the event
    val eventMaximumCapacity: Int, // Maximum number of participants allowed to join the event
    val eventCurrentCapacity: Int, // Current number of participants joined the event
    val eventHost: User,            // Host of the event
    val eventParticipants: List<User>,    // Participants of the event
    val eventApplicant: List<User>,        // Applicant of the event
    val eventStatus: EventStatus,
    val eventCreatedAt: LocalDateTime,       // Created time of the event
    val eventUpdatedAt: LocalDateTime,       // Updated time of the event
)

enum class EventStatus {
    Upcoming,
    Ongoing,
    Completed
}

enum class EventType {
    Running, Cycling, Swimming, Hiking, Yoga, Tennis, Badminton, Basketball, Football
}