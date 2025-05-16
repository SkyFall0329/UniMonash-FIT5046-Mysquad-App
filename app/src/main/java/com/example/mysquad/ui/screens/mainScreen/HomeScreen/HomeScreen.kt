package com.example.mysquad.ui.screens.mainScreen.HomeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysquad.entity.larry.Activity

@Preview
@Composable
fun HomeScreen() {
    val sampleActivities: List<Activity> = listOf(
        Activity("Basketball Training", "2025-04-16 17:30", isHost = true),
        Activity("Morning Run Group", "2025-04-17 06:45", isHost = false),
        Activity("Yoga Relaxation", "2025-04-18 10:00", isHost = true)
    )

    val temperature = "22°C"
    val weatherType = "Clear"
    val hasActivities = sampleActivities.isNotEmpty()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)

    ) {
        // 🟦 App Title
        Text(
            text = "Welcome to PlaySquad",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🌤 Weather Display
        WeatherCard(
            temperature = temperature,
            weatherType = weatherType
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 📋 Section Title
        Text(
            text = "Your Activities",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (hasActivities) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(sampleActivities) { index, activity ->
                    ActivityCard(activity = activity, index = index)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tap the + button below or go to Square to create or join an event 😊",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}


@Composable
fun ActivityCard(activity: Activity, index: Int) {
    val orangeAlt = MaterialTheme.colorScheme.surfaceVariant
    val roleLabel = if (activity.isHost) "Host" else "Participant"
    val roleColor = if (activity.isHost)
        Color(0xFFF65B28)
    else
        Color(0xFF0059FF)

    // 🟡 Alternating background color
    val backgroundColor = if (false)
        MaterialTheme.colorScheme.surface
    else
        orangeAlt

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Activity title
            Text(
                text = activity.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Activity time row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Time",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = activity.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Role tag
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = roleColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = roleLabel,
                    color = roleColor,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}




@Composable
fun WeatherCard(
    temperature: String,
    weatherType: String
) {
    val weatherIcon = when (weatherType.lowercase()) {
        "clear" -> Icons.Default.WbSunny
        "clouds" -> Icons.Default.Cloud
        "rain" -> Icons.Default.Umbrella
        "snow" -> Icons.Default.AcUnit
        else -> Icons.Default.WbCloudy
    }

    val weatherColor = when (weatherType.lowercase()) {
        "clear" -> Color(0xFFFFA726)     // Orange
        "clouds" -> Color(0xFF90A4AE)    // Light Gray-Blue
        "rain" -> Color(0xFF4FC3F7)      // Sky Blue
        "snow" -> Color(0xFF90CAF9)      // Cold Blue
        else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = weatherIcon,
                contentDescription = "Weather icon",
                tint = weatherColor,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Temperature: $temperature",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = when (weatherType.lowercase()) {
                        "clear" -> "Sunny ☀️"
                        "clouds" -> "Cloudy ☁️"
                        "rain" -> "Raining 🌧"
                        "snow" -> "Snowing ❄️"
                        else -> "Unknown 🌫"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
