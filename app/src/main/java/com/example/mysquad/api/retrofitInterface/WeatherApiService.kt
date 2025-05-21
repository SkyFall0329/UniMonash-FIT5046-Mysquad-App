package com.example.mysquad.api.retrofitInterface

import com.example.mysquad.api.data.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/currentConditions:lookup")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("location.latitude") latitude: Double,
        @Query("location.longitude") longitude: Double
    ): WeatherResponse
}