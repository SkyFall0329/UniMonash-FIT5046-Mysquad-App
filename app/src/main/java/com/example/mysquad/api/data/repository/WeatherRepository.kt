package com.example.mysquad.api.data.repository

import com.example.mysquad.api.retrofitInstance.RetrofitInstance
import com.example.mysquad.api.data.dto.WeatherResponse

class WeatherRepository {
    private val api = RetrofitInstance.api
    private val API_KEY = "AIzaSyCoKwYA8ZnmXRFhKBiysdAxH7wKaTYj5mM" // 替换为你自己的 Key

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return api.getCurrentWeather(API_KEY, lat, lon)
    }
}
