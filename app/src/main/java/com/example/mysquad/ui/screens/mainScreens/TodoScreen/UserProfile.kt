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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mysquad.api.data.entityForTesting.jasmine.UserProfileData


@Composable
fun UserProfile(userId: String, onBackClick: () -> Unit) {
    // 模拟用户数据（可替换为后端加载）
    val userData = remember {
        when (userId) {
            "1" -> UserProfileData(
                name = "Ashely Zhu",
                gender = "Female",
                faculty = "IT",
                degree = "Master of Data Science",
                birthday = "1998-06-12",
                favoriteSports = "Basketball",
                bio = "I enjoy playing basketball very much!You can email me to AshelyZhu@student.monash.edu",
                avatarRes = R.drawable.ic_menu_camera
            )
            "2" -> UserProfileData("James Ling", "Male", "Arts", "Bachelor of Music", "2001-09-05", "Dance", "I enjoy popping dance and bbox.", R.drawable.ic_menu_camera)
            "3" -> UserProfileData("Jasmine", "Female", "Science", "Bachelor of Science", "2000-02-28", "Swimming", "I enjoy swimming!", R.drawable.ic_menu_camera)
            "4" -> UserProfileData("Leta Zhang", "Male", "Engineering", "Master of Civil Engineering", "1997-12-08", "All kinds of sports", "I like all kinds of sports", R.drawable.ic_menu_camera)
            "5" -> UserProfileData("Elan Ceres", "Male", "Education", "Bachelor of Education", "1999-11-01", "Reading", "I like reading.", R.drawable.ic_menu_camera)
            "6" -> UserProfileData("Anon Chi", "Female", "Business", "Master of Marketing", "2000-07-18", "Guitar", "I like playing guitar and band activities.", R.drawable.ic_menu_camera)
            else -> UserProfileData("Unknown", "Unknown", "N/A", "N/A", "N/A", "N/A", "N/A", R.drawable.ic_menu_gallery)
        }
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
                Text("Name: ${userData.name}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Gender: ${userData.gender}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Faculty: ${userData.faculty}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Degree: ${userData.degree}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Birthday: ${userData.birthday}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Preferred Sports: ${userData.favoriteSports}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
                HorizontalDivider()
                Text("Description: ${userData.bio}", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
