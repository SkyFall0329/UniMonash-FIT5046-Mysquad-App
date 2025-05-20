package com.example.mysquad.ui.screens.mainScreens.TodoScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mysquad.data.entityForTesting.jianhui.Event
import com.example.mysquad.data.entityForTesting.jianhui.User

/**
 * Event card for displaying event details.
 */
@Composable
fun EventCard(event: Event, onClick: () -> Unit, isHostedByMe: Boolean, currentUser: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${event.eventType} - ${event.eventTitle}", style = MaterialTheme.typography.titleMedium)
                if (isHostedByMe && event.eventApplicant.isNotEmpty()) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ) {
                                Text("${event.eventApplicant.size}")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            }
            Text("${event.eventDate} | ${event.eventStartTime} - ${event.eventEndTime}", style = MaterialTheme.typography.bodyMedium)
            Text("Participants: ${event.eventCurrentCapacity}/${event.eventMaximumCapacity}", style = MaterialTheme.typography.bodySmall)

            // Display status label based on user role
            val statusText = when {
                isHostedByMe -> event.eventStatus.name
                event.eventApplicant.contains(currentUser) -> "Awaiting for Approval"
                event.eventParticipants.contains(currentUser) -> "Joined"
                else -> null
            }

            statusText?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Status: ")
                            withStyle(style = SpanStyle(color = Color(0xFFF85C28))) {
                                append(it)
                            }
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}