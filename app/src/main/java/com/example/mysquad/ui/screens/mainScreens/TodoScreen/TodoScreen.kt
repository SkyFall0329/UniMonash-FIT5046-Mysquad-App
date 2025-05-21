package com.example.mysquad.ui.screens.mainScreens.TodoScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.mysquad.api.data.entityForTesting.jianhui.Event
import com.example.mysquad.api.data.entityForTesting.jianhui.User
import com.example.mysquad.api.data.entityForTesting.jianhui.local.LocalEvent

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
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 36.dp)
        )
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
                        activeContainerColor = Color(0xFFFF7D5C), // Orange
                        activeContentColor = Color(0xFFFFFFFF),
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