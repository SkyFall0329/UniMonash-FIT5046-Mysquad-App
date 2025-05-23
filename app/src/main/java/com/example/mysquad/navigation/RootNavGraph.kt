package com.example.mysquad.navigation

import EventRepository
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.ViewModel.factory.EventViewModelFactory
import com.example.mysquad.data.localRoom.database.AppDatabase
import com.example.mysquad.data.remoteFireStore.EventRemoteDataSource
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    val db = AppDatabase.getInstance(context)
    val remote = EventRemoteDataSource()
    val repository = EventRepository(db.eventDao(), db.userDao(),remote)
    val eventViewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(repository)
    )

    // 🔁 3. Use ViewModel in both graphs
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        navigation(
            startDestination = Screen.Login.route,
            route = Screen.Auth.route
        ) {
            AuthNavGraph(
                navController = navController,
                onThemeChange = onThemeChange,
                authViewModel = authViewModel,
                eventViewModel = eventViewModel
            )
        }

        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Screen.MainGraph.route
        ) {
            MainNavGraph(
                navController = navController,
                onThemeChange = onThemeChange,
                authViewModel = authViewModel,
                eventViewModel = eventViewModel
            )
        }
    }
}