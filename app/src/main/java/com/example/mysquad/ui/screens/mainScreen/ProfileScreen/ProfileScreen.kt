package com.example.mysquad.ui.screens.mainScreen.ProfileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.componets.larry.DisplayDatePicker

import com.example.mysquad.data.entityForTesting.larry.UserProfile
import com.example.mysquad.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(currentUser: UserProfile,
                  viewModel: AuthViewModel,
                  navController: NavController) {
    var isEditing by remember { mutableStateOf(false) }

    var faculty by remember { mutableStateOf(currentUser.faculty) }
    var degree by remember { mutableStateOf(currentUser.degree) }
    var birthday by remember { mutableStateOf(currentUser.birthday) }
    var preferredSports by remember { mutableStateOf(currentUser.favoriteSports) }
    var bio by remember { mutableStateOf(currentUser.bio) }

    val editableFields = listOf(
        "Faculty" to faculty,
        "Degree" to degree,
        "Birthday" to birthday,
        "Preferred Sports" to preferredSports,
        "Bio" to bio
    )

    Scaffold(
        topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "My Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                TextButton(onClick = { isEditing = !isEditing }) {
                    Text(
                        text = if (isEditing) "Save" else "Edit",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            actions = {
                TextButton(onClick = {
                    viewModel.signOut()
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0)
                    }
                }) {
                    Text(
                        text = "Log Out",
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = currentUser.userName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Icon(
                imageVector = when (currentUser.gender.lowercase()) {
                    "male" -> Icons.Default.Male
                    "female" -> Icons.Default.Female
                    else -> Icons.Default.Person
                },
                contentDescription = "Gender",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                item {
                    InfoCard(
                        label = "ID",
                        value = currentUser.userID,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )
                    InfoCard(
                        label = "Email",
                        value = currentUser.userEmail,
                    )
                }

                items(editableFields) { (label, value) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (isEditing) {
                                if (label == "Birthday") {
                                    DisplayDatePicker(
                                        value = birthday,
                                        onValueChange = { birthday = it },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                } else {
                                    OutlinedTextField(
                                        value = value,
                                        onValueChange = {
                                            when (label) {
                                                "Faculty" -> faculty = it
                                                "Degree" -> degree = it
                                                "Preferred Sports" -> preferredSports = it
                                                "Bio" -> bio = it
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            } else {
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun InfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(), // 👈 用外部 modifier 替代硬编码
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}




