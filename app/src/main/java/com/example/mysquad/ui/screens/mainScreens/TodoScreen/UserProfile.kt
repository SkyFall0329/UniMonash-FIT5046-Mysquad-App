package com.example.mysquad.ui.screens.mainScreens.TodoScreen

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.ViewModel.factory.UserProfileViewModelFactory
import com.example.mysquad.api.data.entityForTesting.jasmine.UserProfileData
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.remoteFireStore.UserRemoteDataSource
import com.example.mysquad.data.repository.UserRepository
import kotlin.toString


@Composable
fun UserProfile(userId: String, onBackClick: () -> Unit) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Return"
                )
            }
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Name: ${user?.userName}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Gender: ${user?.userGender}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Faculty: ${user?.userFaculty}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Degree: ${user?.userDegree}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Birthday: ${user?.userBirthday}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Preferred Sports: ${user?.userPreferredSports}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Description: ${user?.userBio}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
