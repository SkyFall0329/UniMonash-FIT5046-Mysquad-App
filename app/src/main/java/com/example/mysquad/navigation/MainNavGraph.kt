package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ui.screens.mainScreen.mainNavi.MainScreen
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(64)
fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel
) {
    composable(Screen.Main.route) {
        MainScreen(
            navController = navController,
            authViewModel = authViewModel,
            onThemeChange = onThemeChange
        )
    }
}