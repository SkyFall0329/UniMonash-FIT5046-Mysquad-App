package com.example.mysquad.ui.screens.mainScreen.HomeScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.mysquad.ViewModel.WeatherViewModel
import com.example.mysquad.entity.larry.Activity
import com.google.android.gms.location.LocationServices


@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationReceived: (Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = com.google.android.gms.location.LocationRequest
        .create()
        .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(1000)
        .setNumUpdates(1) // ✅ 只请求一次

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                val location = result.lastLocation
                if (location != null) {
                    Log.d("LocationDebug", "Real-time Location: ${location.latitude}, ${location.longitude}")
                    onLocationReceived(location)
                } else {
                    Log.w("LocationDebug", "Real-time location is null.")
                }

                // ✅ 结束监听（只用一次）
                fusedLocationClient.removeLocationUpdates(this)
            }
        },
        Looper.getMainLooper()
    )
}


@Preview
@Composable
fun HomeScreen() {
    val viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val context = LocalContext.current
    LaunchedEffect(true) {
        getCurrentLocation(context) {
            Log.d("Location", "Lat: ${it.latitude}, Lon: ${it.longitude}")
            viewModel.fetchWeather(it.latitude, it.longitude)
        }
    }
    val weather = viewModel.weatherState
    val temperature = weather?.temperature?.degrees?.toString()?.plus("°C") ?: "..."
    val condition = weather?.weatherCondition?.description?.text ?: "Loading..."
    val feelsLike = weather?.feelsLikeTemperature?.degrees?.toInt()?.toString()?.plus("°C") ?: "..."



    val sampleActivities: List<Activity> = listOf(
        Activity("Basketball Training", "2025-04-16 17:30", isHost = true),
        Activity("Morning Run Group", "2025-04-17 06:45", isHost = false),
        Activity("Yoga Relaxation", "2025-04-18 10:00", isHost = true)
    )

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
            feelsLike = feelsLike,
            condition = condition
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
    feelsLike: String,
    condition: String
) {
    val conditionLower = condition.lowercase()

    val weatherIcon = when {
        "clear" in conditionLower -> Icons.Default.WbSunny
        "cloud" in conditionLower -> Icons.Default.Cloud
        "rain" in conditionLower -> Icons.Default.Umbrella
        "snow" in conditionLower -> Icons.Default.AcUnit
        else -> Icons.Default.WbCloudy
    }

    val weatherColor = when {
        "clear" in conditionLower -> Color(0xFFFFA726)
        "cloud" in conditionLower -> Color(0xFF90A4AE)
        "rain" in conditionLower -> Color(0xFF4FC3F7)
        "snow" in conditionLower -> Color(0xFF90CAF9)
        else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                Row {
                    Text(
                        text = "Temperature: $temperature",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Feels like: $feelsLike",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = condition,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


