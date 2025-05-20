package com.example.mysquad.ui.screens.mainScreens.SquareScreen

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

@Composable
fun GetPostDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
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
                    text = "xxytfootballtime~",
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 15.dp, bottom = 10.dp, end = 10.dp)
                )
            }

            // Section Blocks
            listOf(
                "Start Time：" to "2025.05.25 15:00",
                "End Time：" to "2025.05.25 17:00",
                "Activity brief：" to "Join us forand make n – come play, stay active, and make new friends!",
                "Headcount：" to "7/12",
                "Address：" to "Wellington Rd, Clayton VIC 3800"
            ).forEach { (label, content) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
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
                    Toast.makeText(context, "This is an event", Toast.LENGTH_SHORT).show()
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

