package com.example.mysquad.ui.screens.mainScreens.NavigationBar

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.ViewModel.factory.UserProfileViewModelFactory
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
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.localRoom.entity.EventEntity
import com.example.mysquad.data.remoteFireStore.UserRemoteDataSource
import com.example.mysquad.data.repository.UserRepository
import com.example.mysquad.navigation.BottomBarItem
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(64)
@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel,
    eventViewModel: EventViewModel
) {
    val navController = rememberNavController()
    val items = BottomBarItem.items
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val userRepository = UserRepository(db.userDao(), UserRemoteDataSource())
    val profileViewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModelFactory(userRepository)
    )
    val userMap by profileViewModel.userMap.collectAsState()

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
                val uid = authViewModel.getCurrentUserId()?:""
                TodoScreen(
                    currentUserUid = uid,
                    viewModel = eventViewModel,
                    profileViewModel = profileViewModel,
                    onCardClick = { eventId -> navController.navigate(Screen.EventDetail.createRoute(eventId)) }
                )
            }
            composable(
                route = Screen.EventDetail.route,
                arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable

                val event by eventViewModel.eventById(eventId)
                    .collectAsState(initial = null)

                if (event != null) {
                    EventDetailScreen(
                        event          = event as EventEntity,
                        currentUserId  = authViewModel.getCurrentUserId()?:"",
                        onNavigateBack = { navController.popBackStack() },
                        navController  = navController,
                        userMap = userMap,
                        viewModel = eventViewModel
                    )
                } else {
                    LoadingScreen(onBack = { navController.popBackStack() })
                }
            }
            composable(route = Screen.RequestsList.route, arguments = listOf(navArgument("eventId") { type = NavType.StringType })
            ) {backStackEntry ->
                val eventId = backStackEntry.arguments?.getString("eventId")
                RequestsList(
                    onAvatarClick = { /* TODO */ },
                    navController = navController,
                    eventId = eventId.toString()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Loading...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}