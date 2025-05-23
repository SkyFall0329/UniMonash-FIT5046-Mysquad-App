package com.example.mysquad.ui.screens.mainScreens.TodoScreen

import android.os.Build
import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.ui.screens.mainScreens.todo.EventCard
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoScreen(
    currentUserUid: String,
    viewModel: EventViewModel,
    profileViewModel: UserProfileViewModel,
    onCardClick: (String) -> Unit          // receives eventId
) {

    LaunchedEffect(Unit) {
        viewModel.syncEventsToLocal()
        profileViewModel.syncAllUsersFromRemote()
        profileViewModel.loadUserFromRoom(currentUserUid)
        profileViewModel.syncUserFromRemote(currentUserUid)
    }

    val userMap by profileViewModel.userMap.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }   // 0 = hosted | 1 = joined

    val hostedEvents  by viewModel.hostedBy(currentUserUid).collectAsState(initial = emptyList())
    val joinedEvents  by viewModel.joinedBy(currentUserUid).collectAsState(initial = emptyList())
    val eventsToShow: List<EventEntity> = if (selectedTab == 0) hostedEvents else joinedEvents

    /* ---------- UI ---------- */
    Column(modifier = Modifier.fillMaxSize()) {

        /** headline **/
        Text(
            text = "My Events",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 36.dp)
        )

        /** segmented control **/
        SingleChoiceSegmentedButton(
            selectedIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        /** list **/
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            itemsIndexed(eventsToShow) { _, event ->
                EventCard(
                    event = event,
                    onClick = { onCardClick(event.eventId) },
                    currentUserUid = currentUserUid,
                    userMap = userMap
                )
            }
        }
    }
}


@Composable
fun SingleChoiceSegmentedButton(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf("Host or Joined", "Pending")

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
                        activeContainerColor   = Color(0xFFFF7D5C),
                        activeContentColor     = Color.White,
                        inactiveContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        inactiveContentColor   = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                    label = { Text(text = label) }
                )
            }
        }
    }
}