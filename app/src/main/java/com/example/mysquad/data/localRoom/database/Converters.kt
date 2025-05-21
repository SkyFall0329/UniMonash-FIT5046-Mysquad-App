package com.example.mysquad.data.localRoom.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String = value?.joinToString(",") ?: ""

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isBlank()) emptyList() else value.split(",")

    @TypeConverter
    fun fromDoubleList(value: List<Double>?): String = value?.joinToString(",") ?: ""

    @TypeConverter
    fun toDoubleList(value: String): List<Double> =
        if (value.isBlank()) emptyList() else value.split(",").map { it.toDouble() }
}