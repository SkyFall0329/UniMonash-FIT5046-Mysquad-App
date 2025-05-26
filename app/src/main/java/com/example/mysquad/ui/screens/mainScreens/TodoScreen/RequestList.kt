package com.example.mysquad.ui.screens.mainScreens.TodoScreen

import EventRepository
import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.factory.EventViewModelFactory
import com.example.mysquad.api.data.entityForTesting.jasmine.FriendRequest
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
import com.example.mysquad.navigation.Screen
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RequestsList(navController: NavHostController, onAvatarClick: (String) -> Unit, eventId: String) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val db = remember { AppDatabase.getInstance(context) }
    val eventDao = db.eventDao()
    val userDao = db.userDao()
    val remote = EventRemoteDataSource()
    val eventRepository = remember { EventRepository(
        eventDao, userDao,remote) }
    val eventViewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(eventRepository)
    )
    val usersState = eventViewModel.pendingUsers.observeAsState(emptyList())
    val users = usersState.value
    LaunchedEffect(Unit) {
        eventViewModel.loadPendingUsers(eventId)
    }

    val friendRequests = users.map {
        FriendRequest(
            id = it.userId,
            name = it.userName,
            avatarResId = R.drawable.ic_menu_camera,
            message = it.userBio?.ifEmpty { "No message." } ?: " "
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Applicant List",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        if (users.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Applicant",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn {
                itemsIndexed(friendRequests) { _, request ->
                    FriendRequestItem(
                        request = request,
                        onAccept = {
                            eventViewModel.acceptUser(eventId, request.id)
                            coroutineScope.launch {
                                eventViewModel.loadPendingUsers(eventId)
                            }
                        },
                        onReject = {
                            eventViewModel.rejectUser(eventId, request.id)
                            coroutineScope.launch {
                                eventViewModel.loadPendingUsers(eventId)
                            }
                        },
                        onAvatarClick = {
                            navController.navigate(Screen.UserProfile.createRoute(request.id))
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun FriendRequestItem(
    request: FriendRequest,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onAvatarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = request.avatarResId),
            contentDescription = "user avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable(onClick = onAvatarClick),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = request.name,
                style = MaterialTheme.typography.titleMedium
            )
            if (request.message.isNotEmpty()) {
                Text(
                    text = request.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Button(
            onClick = onAccept,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(end = 8.dp)
                .height(30.dp)
                .width(40.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
        ) {
            Text("✓", fontSize = 14.sp)
        }

        Button(
            onClick = onReject,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B0000),
                contentColor = Color.White
            ),
            modifier = Modifier
                .height(30.dp)
                .width(40.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
        ) {
            Text("✕", fontSize = 12.sp)
        }
    }
}
