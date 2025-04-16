package com.example.mysquad.ui.screens.mainScreen.TodoScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.*
import com.example.mysquad.entity.jianhui.Event
import com.example.mysquad.entity.jianhui.User
import com.example.mysquad.entity.jianhui.local.LocalEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoScreen(currentUser: User, navigateToDetail: (Int) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val hostedEvents = LocalEvent.events.filter { it.eventHost == currentUser }
    val joinedEvents = LocalEvent.events.filter {
        it.eventParticipants.contains(currentUser) || it.eventApplicant.contains(currentUser)
    }
    val eventsToShow:List<Event>  = if (selectedTab == 0) hostedEvents else joinedEvents

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "My Events",
            style = MaterialTheme.typography.headlineMedium.copy(
                brush = Brush.linearGradient(listOf(Color(0xFFFF7C46), Color(0xFFF85C28))),
                shadow = Shadow(
                    color = Color.Gray,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 36.dp)
        )
        HorizontalDivider(thickness = 3.dp)
        SingleChoiceSegmentedButton(
            selectedIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            itemsIndexed(eventsToShow) { _, event ->
                EventCard(
                    event = event,
                    onClick = { navigateToDetail(event.eventID) },
                    isHostedByMe = (selectedTab == 0),
                    currentUser = currentUser
                )
            }
        }
    }
}

/**
 * Single choice segmented button for selecting between hosted events and joined events.
 */
@Composable
fun SingleChoiceSegmentedButton(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf("Hosted by me", "Joined by me")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = index == selectedIndex,
                    onClick = { onTabSelected(index) },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = Color(0xFFF85C28), // Orange
                        activeContentColor = Color.White,
                        inactiveContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    label = { Text(text = label) }
                )
            }
        }
    }
}