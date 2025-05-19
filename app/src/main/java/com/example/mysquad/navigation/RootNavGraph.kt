package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(64)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = "auth") {
        navigation(startDestination = Screen.Login.route, route = "auth") {
            AuthNavGraph(navController, onThemeChange, authViewModel)
        }
        navigation(startDestination = Screen.Main.route, route = "main") {
            MainNavGraph(
                navController = navController,
                onThemeChange = onThemeChange,
                authViewModel = authViewModel
            )
        }
    }
}