package com.example.mysquad.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysquad.ui.screens.login.LoginScreenWithAnimation
import com.example.mysquad.ui.screens.register.RegisterCompleteScreen
import com.example.mysquad.ui.screens.register.RegisterEmailScreen
import com.example.mysquad.ui.screens.register.RegisterVerifyScreen
import com.example.mysquad.ui.theme.ThemeMode

@Composable
fun AppNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit  // ✅ 参数名必须小写
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreenWithAnimation(
                onLoginClick = { /* ... */ },
                onGoogleSignInClick = { /* ... */ },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.RegisterEmail.route)
                },
                onThemeChange = onThemeChange // ✅ 正确传入
            )
        }

        composable(Screen.RegisterEmail.route) {
            RegisterEmailScreen { email ->
                navController.navigate(Screen.RegisterVerify.route)
            }
        }

        composable(Screen.RegisterVerify.route) {
            RegisterVerifyScreen(email = "user@email.com") {  // 你可用ViewModel传递参数
                navController.navigate(Screen.RegisterComplete.route)
            }
        }

        composable(Screen.RegisterComplete.route) {
            RegisterCompleteScreen {
                navController.navigate(Screen.Login.route)
            }
        }

        composable(Screen.ForgotPassword.route) {
            // ForgotPasswordScreen()
        }
    }
}