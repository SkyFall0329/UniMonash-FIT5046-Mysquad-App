package com.example.mysquad.ui.screens.mainScreens.HomeScreen

import EventRepository
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.WeatherViewModel
import com.example.mysquad.ViewModel.factory.EventViewModelFactory
import com.example.mysquad.api.data.entityForTesting.larry.Activity
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.example.mysquad.componets.util.formatUnixSeconds
import com.example.mysquad.componets.util.getActivityIcon
import com.example.mysquad.workManager.NotificationWorker
import com.example.mysquad.workManager.sendNotification


@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationReceived: (Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = com.google.android.gms.location.LocationRequest
        .create()
        .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(1000)
        .setNumUpdates(1)

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
                fusedLocationClient.removeLocationUpdates(this)
            }
        },
        Looper.getMainLooper()
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val viewModel: WeatherViewModel = viewModel()
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val eventDao = db.eventDao()
    val userDao =db.userDao()
    val remote = EventRemoteDataSource()
    val eventRepository = remember { EventRepository(eventDao, userDao,remote) }
    val eventViewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(eventRepository)
    )
    LaunchedEffect(Unit) {
        eventViewModel.syncFromFirebase2()
        eventViewModel.observeRelevantEvents(userId.toString())
    }

    val relevantEventsState = eventViewModel.relevantEvents.collectAsState()
    val relevantEvents = relevantEventsState.value



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

    val hasActivities = relevantEvents.isNotEmpty()

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
            text = "Your Activities For The Next Seven Days",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (hasActivities) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(relevantEvents) { index, event ->

                    // ✅ 转换 EventEntity → Activity（你定义的数据类）
                    val activity = Activity(
                        type = event.eventType,
                        title = event.eventTitle,
                        time = formatUnixSeconds(event.eventDate), // 将秒转成格式化时间
                        isHost = event.eventHostUserId == userId
                    )

                    ActivityCard(activity = activity, context = context)
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
                    text = "You have no activities scheduled for the next seven days\nTap the + button below or go to Square to create or join an event 😊",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            }
        }
    }
}


@Composable
fun ActivityCard(activity: Activity, context: Context) {
    val orangeAlt = MaterialTheme.colorScheme.surfaceVariant
    val roleLabel = if (activity.isHost) "Host" else "Participant"
    val roleColor = if (activity.isHost)
        Color(0xFFF65B28)
    else
        Color(0xFF0059FF)

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
                text = activity.type + " - " + activity.title,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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

                Button(onClick = {
                    val message = activity.type + " - " + activity.title + " will commence today!"
                    sendNotification(message, context)
                }) {
                    Text("Notify Me!")
                }
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


