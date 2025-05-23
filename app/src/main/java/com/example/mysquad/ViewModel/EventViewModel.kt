package com.example.mysquad.ViewModel

import EventRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysquad.api.data.entityForTesting.jianhui.Event
import com.example.mysquad.data.localRoom.entity.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _events = eventRepository.getLocalEvents().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val events: StateFlow<List<EventEntity>> = _events
    fun hostedBy(uid: String): Flow<List<EventEntity>> =
        eventRepository.getHostEvents(uid)

    fun joinedBy(uid: String): Flow<List<EventEntity>> =
        eventRepository.getPending(uid)


    private val _relevantEvents = MutableStateFlow<List<EventEntity>>(emptyList())
    val relevantEvents: StateFlow<List<EventEntity>> = _relevantEvents

    fun observeRelevantEvents(userId: String) {
        viewModelScope.launch {
            eventRepository.getRelevantEventsAsFlow(userId).collect {
                _relevantEvents.value = it
            }
        }
    }

    fun eventById(id: String): Flow<EventEntity?> =
        eventRepository.eventById(id)

    fun syncEventsToLocal() = viewModelScope.launch(Dispatchers.IO) {
        eventRepository.syncEventsToLocal()
    }


    private val _eventCreated = MutableStateFlow<Boolean?>(null)
    val eventCreated: StateFlow<Boolean?> = _eventCreated

    fun createEvent(event: EventEntity) {
        viewModelScope.launch {
            try {
                eventRepository.createEvent(event)
                _eventCreated.value = true
            } catch (e: Exception) {
                _eventCreated.value = false
            }
        }
    }


    fun getAllEvents(): Flow<List<EventEntity>> {
        return eventRepository.getRemoteEvents()
    }

    fun syncFromFirebase() {
        viewModelScope.launch {
            try {
                eventRepository.syncEventsToLocal()
            } catch (_: Exception) {
            }
        }
    }

    fun syncFromFirebase2() {
        viewModelScope.launch {
            try {
                eventRepository.syncEventsToLocal2()
            } catch (_: Exception) {
            }
        }
    }

    fun resetStatus() {
        _eventCreated.value = null
    }

    suspend fun getPostDetail(postId: String?): EventEntity? {
        return eventRepository.getPostDetail(postId)
    }

    fun joinEvent(value: EventEntity) {
        viewModelScope.launch {
            try {
                eventRepository.joinEvent(value)
            } catch (e: Exception) {
                Log.e("EventViewModel", "Error syncing events", e)
            }
        }
    }

    fun cancelEvent(eventId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            eventRepository.deleteEvent(eventId)
        } catch (e: Exception) {
            Log.e("EventViewModel", "Failed to delete event", e)
        }
    }

}