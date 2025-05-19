package com.example.mysquad.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomBarItem("home_screen", "Home", Icons.Default.Home)
    object Square : BottomBarItem("square_screen", "Square", Icons.Default.Public)
    object Add : BottomBarItem("add_screen", "", Icons.Default.Add) // 中间加号不带文字
    object Todo : BottomBarItem("todo_screen", "Todo", Icons.Default.EventNote)
    object Profile : BottomBarItem("profile_screen", "Profile", Icons.Default.Person)

    companion object {
        val items = listOf(Home, Square, Add, Todo, Profile)
    }
}