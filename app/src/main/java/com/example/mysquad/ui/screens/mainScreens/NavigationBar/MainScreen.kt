package com.example.mysquad.ui.screens.mainScreens.NavigationBar

import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.api.data.entityForTesting.jianhui.local.LocalEvent
import com.example.mysquad.api.data.entityForTesting.jianhui.local.LocalUser
import com.example.mysquad.navigation.Screen
import com.example.mysquad.ui.screens.mainScreens.AddScreen.AddScreen
import com.example.mysquad.ui.screens.mainScreens.HomeScreen.HomeScreen
import com.example.mysquad.ui.screens.mainScreens.ProfileScreen.ProfileScreen
import com.example.mysquad.ui.screens.mainScreens.SquareScreen.GetPostDetail
import com.example.mysquad.ui.screens.mainScreens.SquareScreen.SquareScreen
import com.example.mysquad.ui.screens.mainScreens.TodoScreen.EventDetailScreen
import com.example.mysquad.ui.screens.mainScreens.TodoScreen.RequestsList
import com.example.mysquad.ui.screens.mainScreens.TodoScreen.TodoScreen
import com.example.mysquad.ui.screens.mainScreens.TodoScreen.UserProfile
import com.example.mysquad.api.data.entityForTesting.larry.UserProfile
import com.example.mysquad.navigation.BottomBarItem
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(64)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel,
) {
    val navController = rememberNavController()
    val items = BottomBarItem.items
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected && item.route.isNotBlank()) {
                                navController.navigate(item.route) {
                                    popUpTo(BottomBarItem.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = {
                            if (item.label.isNotEmpty()) {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = Color.Transparent
                        )
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
            composable(Screen.PostDetail.route) {
                GetPostDetail(onBackClick = { navController.popBackStack() }, navController = navController)
            }
            composable(BottomBarItem.Add.route) { AddScreen() }
            composable(Screen.TodoScreen.route) {
                TodoScreen(
                    currentUser = LocalUser.user1,
                    navigateToDetail = { eventId ->
                        navController.navigate(Screen.EventDetail.createRoute(eventId))
                    }
                )
            }
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
                        navController = navController
                    )
                }
            }
            composable(Screen.RequestsList.route) {
                RequestsList(
                    onAvatarClick = { /* TODO */ },
                    navController = navController
                )
            }
            composable(
                route = Screen.UserProfile.route,
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: "unknown"
                UserProfile(
                    userId = userId,
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Screen.ProfileScreen.route) {
                ProfileScreen(
                    viewModel = authViewModel,
                    navController = navController,
                    rootNavController = rootNavController,
                )
            }
        }
    }
}