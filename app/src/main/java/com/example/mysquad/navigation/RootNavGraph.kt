package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ui.screens.forgotpassword.ForgotEmailScreen
import com.example.mysquad.ui.screens.login.LoginScreenWithAnimation
import com.example.mysquad.ui.screens.register.RegisterCompleteScreen
import com.example.mysquad.ui.screens.register.RegisterEmailScreen
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(64)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        // Auth Graph
        navigation(
            startDestination = Screen.Login.route,
            route = Screen.Auth.route
        ) {
            composable(Screen.Login.route) {
                LoginScreenWithAnimation(
                    navController = navController, // 👈 用顶层 controller
                    onThemeChange = onThemeChange,
                    viewModel = authViewModel
                )
            }
            composable(Screen.RegisterEmail.route) {
                RegisterEmailScreen { email ->
                    authViewModel.setTempEmail(email.trim())
                    navController.navigate(Screen.RegisterComplete.route)
                }
            }
            composable(Screen.RegisterComplete.route) {
                RegisterCompleteScreen { username, password ->
                    authViewModel.register(
                        email = authViewModel.tempEmail,
                        pwd = password,
                        username = username
                    )
                }
            }
            composable(Screen.ForgotPassword.route) {
                ForgotEmailScreen(
                    navController = navController,
                    viewModel = authViewModel
                )
            }
        }
        navigation(
            startDestination = Screen.HomeScreen.route,
            route = Screen.MainGraph.route
        ) {
            MainNavGraph(
                navController = navController,
                onThemeChange = onThemeChange,
                authViewModel = authViewModel
            )
        }
    }
}