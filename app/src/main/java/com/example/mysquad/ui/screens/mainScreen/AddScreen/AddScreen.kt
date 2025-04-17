package com.example.mysquad.ui.screens.mainScreen.AddScreen

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.mysquad.R
import com.example.mysquad.componets.ashley.*
import com.example.mysquad.ui.screens.mainScreen.SquareScreen.DisplayDatePicker

@RequiresApi(64)
@Composable
fun AddScreen(modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        val verticalScrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(top = 35.dp)
                .verticalScroll(verticalScrollState),
        ) {

            //标题
            Surface(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = "Create event",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Row {
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "select a type of event",
                        states = listOf("Basketball", "Football", "Volleyball", "Badminton", "Table Tennis", "Tennis", "Swimming", "Aerobics")
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    DisplayDatePicker()
                }
            }

            CustomTextField(
                value = title,
                onValueChange = { title = it },
                label = "Please enter the title of the event"
            )

            // 地址输入框
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = {
                        Text(
                            text = "Please enter address of the event",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(vertical = 8.dp)
                )
            }

            Row {
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "start time",
                        states = listOf("10:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Downregulate(
                        labelText = "end time",
                        states = listOf("14:00", "12:00", "13:00", "15:00", "16:00")
                    )
                }
            }

            CustomTextField(
                value = num,
                onValueChange = { num = it },
                label = "Number of people scheduled for the event"
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.googlemap),
                    contentDescription = "Map preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(vertical = 20.dp)
                )
            }

            //按钮区域
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Cancel */ },
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Button(
                    onClick = { /* Post */ },
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Post",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
