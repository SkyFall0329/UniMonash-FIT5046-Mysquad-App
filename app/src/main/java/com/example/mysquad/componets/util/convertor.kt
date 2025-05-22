package com.example.mysquad.componets.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatUnixSeconds(seconds: Long): String {
    return Instant.ofEpochSecond(seconds)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}

fun timeStringToSeconds(timeStr: String): Long {
    return try {
        val parts = timeStr.split(":")
        val hours = parts[0].toLongOrNull() ?: 0L
        val minutes = parts.getOrNull(1)?.toLongOrNull() ?: 0L
        hours * 3600 + minutes * 60
    } catch (e: Exception) {
        0L
    }
}