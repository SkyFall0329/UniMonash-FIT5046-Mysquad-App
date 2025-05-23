package com.example.mysquad.ui.screens.mainScreens.TodoScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.navigation.Screen
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    event: EventEntity,                     // ← Room entity, not the old stub
    currentUserId: String,
    onNavigateBack: () -> Unit,
    navController: NavHostController
) {
    /* ---------- quick helpers ---------- */
    val isHost   = event.eventHostUserId == currentUserId
    val isJoined = event.eventJoinList.contains(currentUserId)
    val isPending= event.eventPendingList.contains(currentUserId)
    val isParticipantOrApplicant = isJoined || isPending

    val localDate = Instant.ofEpochMilli(event.eventDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val dateFmt   = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var showCancelDialog by remember { mutableStateOf(false) }
    var showExitDialog   by remember { mutableStateOf(false) }

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

            /* 🗓️ EVENT INFO */
            InfoCard {
                Text("Event Type: ${event.eventType}", style = MaterialTheme.typography.bodyLarge)
                Text("Date: ${dateFmt.format(localDate)}   Time: ${event.eventStartTime} – ${event.eventEndTime}")
            }

            /* 👤 HOST & PARTICIPANTS */
            InfoCard {
                Text("Host:", style = MaterialTheme.typography.titleSmall)
                ClickableText(
                    text = AnnotatedString(event.eventHostUserId),           // uid for now
                    onClick = { /* TODO: navigate to host profile */ },
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                )

                Spacer(Modifier.height(8.dp))

                Text("Participants:", style = MaterialTheme.typography.titleSmall)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    event.eventJoinList.forEach { uid ->
                        ClickableText(
                            text = AnnotatedString(uid),
                            onClick = { /* TODO: navigate to profile */ },
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }

            /* 📝 DESCRIPTION */
            InfoCard {
                Text("Description:", style = MaterialTheme.typography.titleSmall)
                Text(event.eventDescription.ifBlank { "No description provided." })
            }

            /* 📍 LOCATION (placeholder map) */
            InfoCard {
                Text("Location: ${event.eventAddress}")
            }


            /* -------------------------------- ACTION BUTTONS -------------------------------- */
            if (isHost) {
                HostActionRow(
                    pendingCount = event.eventPendingList.size,
                    onCancel     = { showCancelDialog = true },
                    onApplicants = { navController.navigate(Screen.RequestsList.route) }
                )
            } else if (isParticipantOrApplicant) {
                ParticipantActionRow { showExitDialog = true }
            }
        }

        /* ---------- dialogs ---------- */
        if (showCancelDialog) {
            ConfirmationDialog(
                title      = "Cancel Event",
                question   = "Are you sure you want to delete this event?",
                confirmLbl = "Delete",
                onConfirm  = { /* TODO: delete event */ },
                onDismiss  = { showCancelDialog = false }
            )
        }

        if (showExitDialog) {
            ConfirmationDialog(
                title      = "Exit Event",
                question   = "Are you sure you want to exit this event?",
                confirmLbl = "Confirm",
                onConfirm  = { /* TODO: exit event */ },
                onDismiss  = { showExitDialog = false }
            )
        }
    }
}

/* ---------- small reusable composables ---------- */

@Composable
private fun InfoCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border   = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
private fun HostActionRow(
    pendingCount: Int,
    onCancel: () -> Unit,
    onApplicants: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick  = onCancel,
            colors   = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cancel Event")
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Delete, contentDescription = "Cancel Event")
        }

        BadgedBox(
            badge = {
                if (pendingCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor   = Color.White
                    ) { Text("$pendingCount") }
                }
            }
        ) {
            Button(onClick = onApplicants) {
                Text("Applicants")
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Filled.Notifications, contentDescription = "Applicants")
            }
        }
    }
}

@Composable
private fun ParticipantActionRow(onExit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onExit,
            colors  = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Exit Event")
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Filled.Delete, contentDescription = "Exit Event")
        }
    }
}

@Composable
private fun ConfirmationDialog(
    title: String,
    question: String,
    confirmLbl: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title            = { Text(title) },
        text             = { Text(question) },
        confirmButton    = {
            TextButton(onClick = onConfirm) { Text(confirmLbl) }
        },
        dismissButton    = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}