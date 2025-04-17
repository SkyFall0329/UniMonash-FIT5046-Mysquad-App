package com.example.mysquad.ui.screens.mainScreen.SquareScreen

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.mysquad.componets.ashley.Downregulate
import com.example.mysquad.navigation.Screen
import androidx.compose.material3.MaterialTheme

@RequiresApi(64)
@Composable
fun SquareScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🟧 Page Title
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

        // 🟦 Filter row: event type + date picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Downregulate(
                    labelText = "Select event type",
                    states = listOf(
                        "Basketball", "Football", "Volleyball", "Badminton",
                        "Table Tennis", "Tennis", "Swimming", "Aerobics"
                    )
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box(modifier = Modifier.weight(1f)) {
                DisplayDatePicker()
            }
        }

        // 📋 Event List
        EventLazyColumn(navController)
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDatePicker(modifier: Modifier = Modifier) {
    val calendar = Calendar.getInstance()
    var selectedDateText by remember { mutableStateOf("") }
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(calendar.timeInMillis) }

    Column(modifier = modifier.padding(vertical = 1.dp)) {
        OutlinedTextField(
            value = selectedDateText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Open date picker",
                    modifier = Modifier
                        .clickable { showDatePicker = true }
                        .size(36.dp)
                )
            }
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis ?: selectedDate
                        selectedDateText = formatter.format(Date(selectedDate))
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}


@Composable
fun EventLazyColumn(navController: NavController) {
    val events = remember {
        mutableStateListOf(
            "677 Basketball", "Title 1 Basketball", "Local Game",
            "Title 3 - Weekly Match", "Friendly Match", "Open Practice", "Evening Session"
        )
    }

    val participantInfo = remember {
        mutableStateListOf("[3/7]", "[3/7]", "[3/7]", "[3/6]", "[5/12]", "[3/7]", "[3/7]")
    }

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        LazyColumn {
            items(events.size) { index ->
                SportsEventCard(
                    eventTitle = events[index],
                    num = participantInfo[index],
                    onClick = { navController.navigate(Screen.PostDetail.route) }
                )
            }
        }
    }
}

@Composable
fun SportsEventCard(
    modifier: Modifier = Modifier,
    eventTitle: String,
    num: String,
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

        Text(
            text = num,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 30.dp),
        )
    }
}



