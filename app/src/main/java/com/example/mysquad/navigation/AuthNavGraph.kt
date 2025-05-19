package com.example.mysquad.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ui.screens.login.LoginScreenWithAnimation
import com.example.mysquad.ui.theme.ThemeMode

fun NavGraphBuilder.AuthNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel
) {
    composable(Screen.Login.route) {
        LoginScreenWithAnimation(
            navController = navController,
            viewModel = authViewModel,
            onThemeChange = onThemeChange
        )
    }
}