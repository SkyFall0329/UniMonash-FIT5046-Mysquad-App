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

@RequiresApi(64)
@Composable
fun SquareScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        ) {
            Text(
                text = "Join an Event",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFFFF6F00),
                modifier = Modifier
                    .padding(start = 10.dp, top = 20.dp, bottom = 20.dp, end = 10.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Box( modifier =  Modifier.weight(1.0f)) {
                Downregulate(
                    labelText = "select a type of event",
                    states = listOf("Basketball", "Football", "Volleyball", "Badminton",
                        "Table Tennis", "Tennis", "Swimming", "Aerobics")
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Box( modifier =  Modifier.weight(1.0f)) {
                DisplayDatePicker()
            }
        }
        Eventlazycolumn(navController)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDatePicker(modifier: Modifier = Modifier) {
    val calendar = Calendar.getInstance()
    var birthday by remember { mutableStateOf("") }
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(calendar.timeInMillis) }


    Column(modifier = Modifier.padding(vertical = 1.dp)) {
        OutlinedTextField(
            value = birthday,
            onValueChange = {},
            readOnly = true,
            label = { Text("select date") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            //0xFFFFE4D6
            colors = TextFieldDefaults.colors(Color(0xFFFFECDB)),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date",
                    modifier = Modifier
                        .clickable { showDatePicker = true }
                        .size(36.dp)
                )
            }
        )


        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        selectedDate = datePickerState.selectedDateMillis!!
                        birthday = " ${formatter.format(Date(selectedDate))}"
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) //end of dialog
            { //still column scope
                DatePicker(
                    state = datePickerState
                )
            }
        }// end of if
    }
}


@Composable
fun Eventlazycolumn(navController: NavController) {
    val events = remember {
        mutableStateListOf(
            "677 Basketball", "title1 Basketball", "677 Basketball",
            "title3xxx5555 Basketball", "title-677lllBasketball", "677 Basketball", "677yyyy Basketball"
        )
    }
    val numbers = remember {
        mutableStateListOf("[3/7]", "[3/7]", "[3/7]", "[3/6]", "[5/12]", "[3/7]", "[3/7]")
    }

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        LazyColumn {
            items(events.size) { index ->
                SportsEventCard(
                    eventTitle = events[index],
                    num = numbers[index],
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
        modifier = Modifier
            .clickable(onClick = {
                Toast.makeText(context, eventTitle, Toast.LENGTH_SHORT).show()
                onClick() // 👈 执行跳转逻辑
            })
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .background(Color(0xFFFFE4D6), RoundedCornerShape(10.dp))
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            text = eventTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 30.dp)
                .weight(1.0f),
        )

        Text(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            text = num,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 30.dp),
        )
    }
}



