package com.example.mysquad.data.dto


data class WeatherResponse(
    val currentTime: String,
    val timeZone: TimeZone,
    val isDaytime: Boolean,
    val weatherCondition: WeatherCondition,
    val temperature: Temperature,
    val feelsLikeTemperature: Temperature,
    val relativeHumidity: Int
)

data class TimeZone(val id: String)

data class WeatherCondition(
    val iconBaseUri: String,
    val description: WeatherDescription,
    val type: String
)

data class WeatherDescription(
    val text: String,
    val languageCode: String
)

data class Temperature(
    val degrees: Double,
    val unit: String
)