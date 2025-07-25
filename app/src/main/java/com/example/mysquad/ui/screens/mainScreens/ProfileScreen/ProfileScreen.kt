package com.example.mysquad.ui.screens.mainScreens.ProfileScreen

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.ViewModel.factory.UserProfileViewModelFactory
import com.example.mysquad.componets.larry.DisplayDatePicker
import com.example.mysquad.api.data.entityForTesting.larry.UserProfile
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import com.example.mysquad.data.remoteFireStore.UserRemoteDataSource
import com.example.mysquad.data.repository.UserRepository
import com.example.mysquad.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
                  viewModel: AuthViewModel,
                  navController: NavController,
                  rootNavController: NavController) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val context = LocalContext.current

    val db = remember { AppDatabase.getInstance(context) }
    val userDao = db.userDao()
    val remote = UserRemoteDataSource()
    val userRepository = UserRepository(userDao, remote)

    val profileViewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModelFactory(userRepository)
    )

    val user by profileViewModel.user.collectAsState()


    LaunchedEffect(Unit) {
        profileViewModel.loadUserFromRoom(userId.toString())
        profileViewModel.syncUserFromRemote(userId.toString())

    }



    var isEditing by remember { mutableStateOf(false) }
    var shouldShowToast by remember { mutableStateOf(false) }
    LaunchedEffect(shouldShowToast) {
        if (shouldShowToast) {
            Toast.makeText(context, "Profile saved!", Toast.LENGTH_SHORT).show()
            shouldShowToast = false  // ✅ 触发后重置
        }
    }
    var username by remember(user) { mutableStateOf(user?.userName ?: "") }
    var faculty by remember(user) { mutableStateOf(user?.userFaculty ?: "") }
    var degree by remember(user) { mutableStateOf(user?.userDegree ?: "") }
    var gender by remember(user) { mutableStateOf(user?.userGender ?: "") }
    var birthday by remember(user) { mutableStateOf(user?.userBirthday ?: "") }
    var preferredSports by remember(user) { mutableStateOf(user?.userPreferredSports ?: "") }
    var bio by remember(user) { mutableStateOf(user?.userBio ?: "") }

    val editableFields = listOf(
        "Faculty" to faculty,
        "Degree" to degree,
        "Gender" to gender,
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
                TextButton(onClick = {
                    if (isEditing) {
                        userId?.let { uid ->
                            val updatedUser = UserProfileEntity(
                                userId = uid,
                                userName = user?.userName ?: "",
                                userEmail = user?.userEmail ?: "",
                                userGender = gender,
                                userFaculty = faculty,
                                userDegree = degree,
                                userBirthday = birthday,
                                userPreferredSports = preferredSports,
                                userBio = bio,
                                userUpdatedAt = System.currentTimeMillis()
                            )
                            profileViewModel.updateUser(updatedUser)
                            shouldShowToast = true
                        }
                    }
                    isEditing = !isEditing
                }) {
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
                    rootNavController.navigate(Screen.Login.route) {
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
                text = username,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Icon(
                imageVector = when (gender) {
                    "Male" -> Icons.Default.Male
                    "Female" -> Icons.Default.Female
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
                        value = userId.toString(),
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )
                    InfoCard(
                        label = "Email",
                        value = user?.userEmail.toString(),
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
                                }
                                else if(label == "Gender"){
                                    var genderExpanded by remember { mutableStateOf(false) }
                                    val genderOptions = listOf("Male", "Female", "Other")
                                    ExposedDropdownMenuBox(
                                        expanded = genderExpanded,
                                        onExpandedChange = { genderExpanded = !genderExpanded }
                                    ) {
                                        OutlinedTextField(
                                            value = gender,
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                                            modifier = Modifier.menuAnchor().fillMaxWidth()
                                        )

                                        ExposedDropdownMenu(
                                            expanded = genderExpanded,
                                            onDismissRequest = { genderExpanded = false }
                                        ) {
                                            genderOptions.forEach { option ->
                                                DropdownMenuItem(
                                                    text = { Text(option) },
                                                    onClick = {
                                                        gender = option
                                                        genderExpanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                else if(label == "Faculty"){
                                    var facultyExpanded by remember { mutableStateOf(false) }
                                    val facultyOptions = listOf(
                                        "Arts",
                                        "Business and Economics",
                                        "Education",
                                        "Engineering",
                                        "Information Technology",
                                        "Law",
                                        "Medicine, Nursing and Health Sciences",
                                        "Pharmacy and Pharmaceutical Sciences",
                                        "Science"
                                    )
                                    ExposedDropdownMenuBox(
                                        expanded = facultyExpanded,
                                        onExpandedChange = { facultyExpanded = !facultyExpanded }
                                    ) {
                                        OutlinedTextField(
                                            value = faculty,
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = facultyExpanded) },
                                            modifier = Modifier.menuAnchor().fillMaxWidth()
                                        )

                                        ExposedDropdownMenu(
                                            expanded = facultyExpanded,
                                            onDismissRequest = { facultyExpanded = false }
                                        ) {
                                            facultyOptions.forEach { option ->
                                                DropdownMenuItem(
                                                    text = { Text(option) },
                                                    onClick = {
                                                        faculty = option
                                                        facultyExpanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                else if(label == "Degree"){
                                    val degreeOptions = listOf(
                                        "Bachelor's",
                                        "Master's",
                                        "PhD",
                                        "Diploma",
                                        "Graduate Certificate",
                                        "Graduate Diploma",
                                        "Honours"
                                    )
                                    var degreeExpanded by remember { mutableStateOf(false) }
                                    ExposedDropdownMenuBox(
                                        expanded = degreeExpanded,
                                        onExpandedChange = { degreeExpanded = !degreeExpanded }
                                    ) {
                                        OutlinedTextField(
                                            value = degree,
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = degreeExpanded) },
                                            modifier = Modifier.menuAnchor().fillMaxWidth()
                                        )

                                        ExposedDropdownMenu(
                                            expanded = degreeExpanded,
                                            onDismissRequest = { degreeExpanded = false }
                                        ) {
                                            degreeOptions.forEach { option ->
                                                DropdownMenuItem(
                                                    text = { Text(option) },
                                                    onClick = {
                                                        degree = option
                                                        degreeExpanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                else {
                                    OutlinedTextField(
                                        value = value,
                                        onValueChange = {
                                            when (label) {
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
        modifier = modifier.fillMaxWidth(),
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




