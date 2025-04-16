package com.example.mysquad.ui.screens.mainScreen.mainNavi

import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mysquad.entity.jianhui.local.LocalEvent
import com.example.mysquad.entity.jianhui.local.LocalUser
import com.example.mysquad.navigation.Screen
import com.example.mysquad.ui.screens.mainScreen.AddScreen.AddScreen
import com.example.mysquad.ui.screens.mainScreen.HomeScreen.HomeScreen
import com.example.mysquad.ui.screens.mainScreen.ProfileScreen.ProfileScreen
import com.example.mysquad.ui.screens.mainScreen.SquareScreen.GetPostDetail
import com.example.mysquad.ui.screens.mainScreen.SquareScreen.SquareScreen
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.EventDetailScreen
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.RequestsList
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.TodoScreen
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.UserProfile

@RequiresApi(64)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = BottomBarItem.items
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(BottomBarItem.Home.route)
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            if (item.label.isNotEmpty()) Text(item.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomBarItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomBarItem.Home.route) { HomeScreen() }
            composable(BottomBarItem.Square.route) { SquareScreen(navController) }
            composable(Screen.PostDetail.route) { GetPostDetail() }
            composable(BottomBarItem.Add.route) { AddScreen() }
            composable(Screen.TodoScreen.route) { TodoScreen(
                currentUser = LocalUser.user1,
                navigateToDetail = { eventId ->
                    navController.navigate(Screen.EventDetail.createRoute(eventId))
                }
            ) }
            composable(
                route = Screen.EventDetail.route,
                arguments = listOf(navArgument("eventId") { type = NavType.IntType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable

                val event = LocalEvent.events.find { it.eventID == eventId }
                val currentUser = LocalUser.user1

                if (event != null && currentUser != null) {
                    EventDetailScreen(
                        event = event,
                        currentUser = currentUser,
                        onNavigateBack = { navController.popBackStack() },
                        navController = navController,
                    )
                }
            }
            composable(Screen.RequestsList.route) {
                RequestsList(
                    onAvatarClick = { userId ->
                        // navController.navigate(Screen.UserProfile.createRoute(userId))
                    },navController = navController
                )
            }
            composable(
                route = Screen.UserProfile.route,
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: "unknown"
                UserProfile(
                    userId = userId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(BottomBarItem.Profile.route) { ProfileScreen() }
        }
    }
}