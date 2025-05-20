package com.example.mysquad.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysquad.data.dto.WeatherResponse
import com.example.mysquad.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    var weatherState by mutableStateOf<WeatherResponse?>(null)
        private set

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherState = repository.getWeather(lat, lon)
        }
    }
}