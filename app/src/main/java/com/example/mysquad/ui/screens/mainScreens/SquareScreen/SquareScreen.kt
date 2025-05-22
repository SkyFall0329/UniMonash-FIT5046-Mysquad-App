package com.example.mysquad.ui.screens.mainScreens.SquareScreen

import EventRepository
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import java.time.Instant
import com.example.mysquad.componets.ashley.Downregulate
import com.example.mysquad.navigation.Screen
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.factory.EventViewModelFactory
import com.example.mysquad.componets.ashley.DisplayDatePicker
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(64)
@Composable
fun SquareScreen(navController: NavController, modifier: Modifier = Modifier) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Page Title
        Surface(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Text(
                text = "Activity Square",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Downregulate(
                    labelText = "Select event type",
                    selectedState = remember { mutableStateOf("") },
                    states = listOf(
                        "Basketball", "Football", "Volleyball", "Badminton",
                        "Table Tennis", "Tennis", "Swimming", "Aerobics"
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier.weight(1f)) {
                DisplayDatePicker(datePickerState = datePickerState)
            }
        }


        EventLazyColumn(navController)
    }
}




@Composable
fun EventLazyColumn(navController: NavController) {
    val context = LocalContext.current
    // 手动构造依赖
    val db = remember { AppDatabase.getInstance(context) }
    val eventDao = db.eventDao()
    val remote = EventRemoteDataSource()
    val eventRepository = EventRepository(eventDao, remote)

    val viewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(eventRepository)
    )

    // 插入数据
//    viewModel.insertEvent(
//        Event(2,EventType.Yoga,"1231","1231","123","1231",
//        "1231", emptyList(), LocalDateTime.now(),LocalDateTime.now(),123,
//            123,123, User("123","123"),
//            listOf(User("123","123")), LocalDateTime.now(), LocalDateTime.now())
//    )
    // 删除event数据
//    viewModel.deleteEvent(Event(1,EventType.Yoga,"1231","1231","123","1231",
//        "1231", emptyList(), LocalDateTime.now(),LocalDateTime.now(),123,
//            123,123, User("123","123"),
//            listOf(User("123","123")), LocalDateTime.now(), LocalDateTime.now()))
//
//    viewModel.deleteEvent(Event(2,EventType.Yoga,"1231","1231","123","1231",
//        "1231", emptyList(), LocalDateTime.now(),LocalDateTime.now(),123,
//        123,123, User("123","123"),
//        listOf(User("123","123")), LocalDateTime.now(), LocalDateTime.now()))
//
//    viewModel.deleteEvent(Event(3,EventType.Yoga,"1231","1231","123","1231",
//        "1231", emptyList(), LocalDateTime.now(),LocalDateTime.now(),123,
//        123,123, User("123","123"),
//        listOf(User("123","123")), LocalDateTime.now(), LocalDateTime.now()))

//    LaunchedEffect(Unit) {
//        viewModel.syncFromFirebase()
//    }
    // 读取event数据
    val eventList by viewModel.getAllEvents().collectAsState(emptyList())

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        LazyColumn {
            items(eventList.size) { index ->
                SportsEventCard(
                    eventTitle = eventList[index].eventTitle,
                    onClick = { navController.navigate(Screen.PostDetail.postId(eventList[index].eventId)) }
                )
            }
        }
    }
}

@Composable
fun SportsEventCard(
    modifier: Modifier = Modifier,
    eventTitle: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Row(

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable {
                Toast.makeText(context, eventTitle, Toast.LENGTH_SHORT).show()
                onClick()
            }
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = eventTitle,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black // 强制设为黑色
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 30.dp)
                .weight(1.0f),
        )
    }
}



