package com.example.mysquad.ui.screens.mainScreens.SquareScreen

import EventRepository
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mysquad.R
import com.example.mysquad.componets.larry.TopBarWithBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.factory.EventViewModelFactory
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
import com.google.firebase.auth.FirebaseAuth

@Composable
fun GetPostDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    navController: NavController
) {
    val postId = navController.currentBackStackEntry?.arguments?.getString("eventId")
    val context = LocalContext.current
    // 手动构造依赖
    val db = remember { AppDatabase.getInstance(context) }
    val eventDao = db.eventDao()
    val remote = EventRemoteDataSource()
    val eventRepository = EventRepository(eventDao, remote)

    val viewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(eventRepository)
    )
    val eventEntityState = remember { mutableStateOf<EventEntity>(EventEntity()) }

    LaunchedEffect(postId) {
        val eventEntity = viewModel.getPostDetail(postId)

        if (eventEntity != null) {
            eventEntityState.value = eventEntity
        }
    }

    val verticalScrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBarWithBack(title = "Event Detail", onBackClick = onBackClick)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(verticalScrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Title
            Surface(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            ) {
                Text(
                    text = eventEntityState.value.eventAddress,
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 15.dp, bottom = 10.dp, end = 10.dp)
                )
            }

            // Section Blocks
            listOf(
                "Start Time：" to eventEntityState.value.eventStartTime,
                "End Time：" to eventEntityState.value.eventEndTime,
                "Description：" to eventEntityState.value.eventDescription,
                "Address：" to eventEntityState.value.eventAddress
            ).forEach { (label, content) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.weight(1.2f),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                    Text(
                        text = content,
                        modifier = Modifier.weight(2f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                HorizontalDivider(thickness = 2.dp)
            }

            // Google map image
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.googlemap),
                    contentDescription = "Google Map",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            // Join button
            ElevatedButton(
                onClick = {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId == null || userId == "") {
                        return@ElevatedButton
                    }
                    if (eventEntityState.value.eventJoinList.contains(userId)) {
                        Toast.makeText(context, "You have already joined this event", Toast.LENGTH_SHORT).show()
                        return@ElevatedButton
                    }
                    if (eventEntityState.value.eventPendingList.contains(userId)) {
                        Toast.makeText(context, "You have already applied to join this event", Toast.LENGTH_SHORT).show()
                        return@ElevatedButton
                    }
                    eventEntityState.value.eventPendingList += userId
                    viewModel.joinEvent(eventEntityState.value)
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFFFF6F00))
            ) {
                Text(
                    text = "Apply to join",
                    color = Color.White,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

