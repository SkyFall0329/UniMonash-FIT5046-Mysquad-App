package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysquad.ui.screens.forgotpassword.ForgotEmailScreen
import com.example.mysquad.ui.screens.forgotpassword.ForgotResetPasswordScreen
import com.example.mysquad.ui.screens.forgotpassword.ForgotVerifyScreen
import com.example.mysquad.ui.screens.login.LoginScreenWithAnimation
import com.example.mysquad.ui.screens.mainScreen.mainNavi.MainScreen
import com.example.mysquad.ui.screens.register.RegisterCompleteScreen
import com.example.mysquad.ui.screens.register.RegisterEmailScreen
import com.example.mysquad.ui.screens.register.RegisterVerifyScreen
import com.example.mysquad.ui.theme.ThemeMode

@RequiresApi(64)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreenWithAnimation(
                navController = navController,
                onThemeChange = onThemeChange
            )
        }
        /** ---------- Register Flow ----------- **/
        composable(Screen.RegisterEmail.route) {
            RegisterEmailScreen { email ->
                navController.navigate(Screen.RegisterVerify.route)
            }
        }

        composable(Screen.RegisterVerify.route) {
            RegisterVerifyScreen(
                email = "",          // supply real email from previous screen
                onVerifySuccess = { navController.navigate(Screen.RegisterComplete.route) },
                onResendCode = { /* TODO */ }
            )
        }

        composable(Screen.RegisterComplete.route) {
            RegisterCompleteScreen {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
        /** ---------- FORGOT-PASSWORD FLOW ---------- **/
        composable(Screen.ForgotPassword.route) {
            ForgotEmailScreen {
                navController.navigate(Screen.ForgotPasswordVerify.route)
            }
        }

        composable(Screen.ForgotPasswordVerify.route) {
            ForgotVerifyScreen(
                email = "",          // supply real email
                onVerifySuccess = { navController.navigate(Screen.ForgotPasswordReset.route) },
                onResendCode = { /* TODO */ }
            )
        }

        composable(Screen.ForgotPasswordReset.route) {
            ForgotResetPasswordScreen { /* newPassword -> */
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        }
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}