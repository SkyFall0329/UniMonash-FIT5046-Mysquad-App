package com.example.mysquad.ViewModel.factory

import EventRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysquad.ViewModel.EventViewModel

class EventViewModelFactory(
    private val eventRepository: EventRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}