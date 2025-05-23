package com.example.mysquad.ui.screens.mainScreens.todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mysquad.data.localRoom.entity.EventEntity
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * One card in “My Events” list.
 *
 * @param event          Row coming straight from Room
 * @param onClick        Navigate to detail
 * @param isHostedByMe   True when we are on the “Hosted by me” tab
 * @param currentUserUid Logged-in Firebase uid
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventCard(
    event: EventEntity,
    onClick: () -> Unit,
    currentUserUid: String,
    userMap: Map<String, String>
) {
    val localDate = Instant.ofEpochMilli(event.eventDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val hostName = userMap[event.eventHostUserId] ?: event.eventHostUserId

    val status = when {
        event.eventHostUserId == currentUserUid -> "Host"
        event.eventPendingList.contains(currentUserUid) -> "Awaiting approval"
        event.eventJoinList.contains(currentUserUid) -> "Joined"
        else -> null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${event.eventType} – ${event.eventTitle}",
                    style = MaterialTheme.typography.titleMedium
                )

                if (event.eventHostUserId == currentUserUid && event.eventPendingList.isNotEmpty()) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) { Text("${event.eventPendingList.size}") }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Pending applicants"
                        )
                    }
                }
            }

            Text("Host: $hostName", style = MaterialTheme.typography.bodySmall)
            Text("${dateFmt.format(localDate)} | ${event.eventStartTime}–${event.eventEndTime}", style = MaterialTheme.typography.bodyMedium)
            Text("Participants: ${event.eventJoinList.size}", style = MaterialTheme.typography.bodySmall)

            status?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Status: ")
                            withStyle(style = SpanStyle(color = Color(0xFFF85C28))) { append(it) }
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

