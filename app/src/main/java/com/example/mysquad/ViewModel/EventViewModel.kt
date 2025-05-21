package com.example.mysquad.ViewModel

import EventRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysquad.data.localRoom.entity.EventEntity
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
    private val _relevantEvents = MutableStateFlow<List<EventEntity>>(emptyList())
    val relevantEvents: StateFlow<List<EventEntity>> = _relevantEvents

    fun observeRelevantEvents(userId: String) {
        viewModelScope.launch {
            eventRepository.getRelevantEventsAsFlow(userId).collect {
                _relevantEvents.value = it
            }
        }
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

    fun syncFromFirebase() {
        viewModelScope.launch {
            try {
                eventRepository.syncEventsToLocal()
            } catch (_: Exception) {
            }
        }
    }

    fun resetStatus() {
        _eventCreated.value = null
    }
}