package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ViewModel.EventViewModel
import com.example.mysquad.ViewModel.UserProfileViewModel
import com.example.mysquad.ui.screens.mainScreens.NavigationBar.MainScreen
import com.example.mysquad.ui.theme.ThemeMode


@RequiresApi(64)
fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel,
    eventViewModel: EventViewModel
) {
    composable(Screen.HomeScreen.route) {
        MainScreen(
            onThemeChange = onThemeChange,
            authViewModel = authViewModel,
            rootNavController = navController,
            eventViewModel = eventViewModel
        )
    }
}