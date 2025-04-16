package com.example.mysquad.ui.screens.mainScreen.HomeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mysquad.entity.larry.Activity

@Composable
fun HomeScreen() {
    var sampleActivities: List<Activity> = listOf(
        Activity("篮球训练", "2025-04-16 17:30", isHost = true),
        Activity("晨跑团", "2025-04-17 06:45", isHost = false),
        Activity("瑜伽放松", "2025-04-18 10:00", isHost = true)
    )

    val temperature = "22°C"
    val weatherType = "Clear"
    val hasActivities = sampleActivities.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // 🟦 顶部标题
        Text(
            text = "PlaySquad",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🌤 天气展示
        WeatherCard(
            temperature = temperature,
            weatherType = weatherType
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 📋 活动标题
        Text(
            text = "Your Activities",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (hasActivities) {
            val activityCards = sampleActivities.map { activity ->
                @Composable {
                    ActivityCard(activity = activity)
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                activityCards.forEach { card ->
                    item { card() }
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
                    text = "点击下方加号或者 Square 去创建或加入活动吧 😊",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}
@Composable
fun ActivityCard(activity: Activity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 活动名称
            Text(
                text = activity.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 时间行
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Time",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = activity.time,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 标签
            val tagText = if (activity.isHost) "主持" else "参与"
            val tagColor = if (activity.isHost) Color(0xFF4CAF50) else Color(0xFF2196F3)

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = tagColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = tagText,
                    color = tagColor,
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
    weatherType: String // e.g., "Clear", "Clouds", "Rain"
) {
    val weatherIcon = when (weatherType.lowercase()) {
        "clear" -> Icons.Default.WbSunny
        "clouds" -> Icons.Default.Cloud
        "rain" -> Icons.Default.Umbrella
        "snow" -> Icons.Default.AcUnit
        else -> Icons.Default.WbCloudy
    }

    val weatherColor = when (weatherType.lowercase()) {
        "clear" -> Color(0xFFFFA726)
        "clouds" -> Color.Gray
        "rain" -> Color(0xFF4FC3F7)
        "snow" -> Color(0xFF90CAF9)
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = weatherIcon,
                contentDescription = "Weather",
                tint = weatherColor,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "当前温度：$temperature",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = when (weatherType.lowercase()) {
                        "clear" -> "Sunny ☀️"
                        "clouds" -> "Cloudy ☁️"
                        "rain" -> "Raining 🌧"
                        "snow" -> "Snowing ❄️"
                        else -> "Unknown 🌫"
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}