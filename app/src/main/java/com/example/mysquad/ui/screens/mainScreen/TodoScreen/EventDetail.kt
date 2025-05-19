package com.example.mysquad.ui.screens.mainScreen.TodoScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mysquad.data.entityForTesting.jianhui.Event
import com.example.mysquad.data.entityForTesting.jianhui.User
import com.example.mysquad.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(event: Event, currentUser: User, onNavigateBack: () -> Unit, navController: NavHostController,) {
    val isHost = event.eventHost == currentUser
    val isParticipantOrApplicant = event.eventParticipants.contains(currentUser) || event.eventApplicant.contains(currentUser)
    var showCancelDialog by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(event.eventTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🗓️ Event Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Event Type: ${event.eventType}", style = MaterialTheme.typography.bodyLarge)
                    Text("Date: ${event.eventDate}          Time: ${event.eventStartTime} - ${event.eventEndTime}")
                }
            }

            // 👤 Host Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Host:", style = MaterialTheme.typography.titleSmall)
                    ClickableText(
                        text = AnnotatedString(event.eventHost.userName),
                        onClick = { /* TODO: Navigate to host profile */ },
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                    )
                    Column (modifier = Modifier.padding(top = 8.dp)) {
                        Text("Participants:", style = MaterialTheme.typography.titleSmall)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            event.eventParticipants.forEach { participant ->
                                ClickableText(
                                    text = AnnotatedString(participant.userName),
                                    onClick = { /* TODO: Navigate to participant profile */ },
                                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                    }
                }
            }

            // 📝 Description
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Description:", style = MaterialTheme.typography.titleSmall)
                    Text(event.eventDescription)
                }
            }


            // Showing the location
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Location: ${event.eventAddress}")
                    Text("(Map to be add in the future)")
                }
            }

            // Showing the weather
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Weather: Sunny            Temperature: 36°C")
                    Text("(Weather to be add in the future)")
                }
            }

            // 🔘 Action Buttons
            if (isHost) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { showCancelDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cancel Event")
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Filled.Delete, contentDescription = "Cancel Event")
                    }

                    BadgedBox(
                        badge = {
                            if (event.eventApplicant.isNotEmpty()) {
                                Badge(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ) {
                                    Text("${event.eventApplicant.size}")
                                }
                            }
                        }
                    ) {
                        Button(onClick = {
                            navController.navigate(Screen.RequestsList.route)
                        }) {
                            Text("Applicants")
                            Spacer(Modifier.width(8.dp))
                            Icon(Icons.Filled.Notifications, contentDescription = "Applicants")
                        }
                    }
                }
            } else if (isParticipantOrApplicant) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { showExitDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Exit Event")
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Filled.Delete, contentDescription = "Exit Event")
                    }
                }
            }
        }

        // ❗ Confirmation Dialogs
        if (showCancelDialog) {
            AlertDialog(
                onDismissRequest = { showCancelDialog = false },
                title = { Text("Cancel Event") },
                text = { Text("Are you sure to delete the event?") },
                confirmButton = {
                    TextButton(onClick = { /* TODO: Delete event */ }) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { showCancelDialog = false }) { Text("Cancel") }
                }
            )
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Exit Event") },
                text = { Text("Are you sure you want to exit this event?") },
                confirmButton = {
                    TextButton(onClick = { /* TODO: Confirm exit */ }) { Text("Confirm") }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}