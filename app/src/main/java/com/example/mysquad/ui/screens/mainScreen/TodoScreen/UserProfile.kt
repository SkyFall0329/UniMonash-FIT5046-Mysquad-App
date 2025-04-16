package com.example.mysquad.ui.screens.mainScreen.TodoScreen

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
import androidx.compose.material3.Divider
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
import com.example.mysquad.entity.jasmine.UserProfileData


@Composable
fun UserProfile(userId: String, onBackClick: () -> Unit) {
    // 在实际应用中，你会基于userId获取用户数据
    // 这里使用模拟数据
    val userData = remember {
        when (userId) {
            "1" -> UserProfileData("Ashely Zhu", "Female", "I enjoy playing basketball very much!You can email me to AshelyZhu@student.monash,edu", android.R.drawable.ic_menu_camera)
            "2" -> UserProfileData("James Ling", "Male", "I enjoy popping dance and bbox.", android.R.drawable.ic_menu_camera)
            "3" -> UserProfileData("Jasmine", "Female", "I enjoy swimming!", android.R.drawable.ic_menu_camera)
            "4" -> UserProfileData("Leta Zhang", "Male", "I like all kinds of sports", android.R.drawable.ic_menu_camera)
            "5" -> UserProfileData("Elan Ceres", "Male", "I like reading.", android.R.drawable.ic_menu_camera)
            "6" -> UserProfileData("Anon Chi", "Female", "I like playing guitar and band activities.", android.R.drawable.ic_menu_camera)
            else -> UserProfileData("Unknown", "Unknown", "N/A", android.R.drawable.ic_menu_gallery)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 顶部栏带有返回按钮
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

        // 用户资料内容
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // 头像
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

        // 用户信息
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Name: ${userData.name}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Divider()
                Text(
                    text = "Gender: ${userData.gender}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Divider()
                Text(
                    text = "Description: ${userData.bio}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}