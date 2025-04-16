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
                onLoginClick = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onGoogleSignInClick = { /* ... */ },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.RegisterEmail.route)
                },
                onThemeChange = onThemeChange
            )
        }
        
        composable(Screen.RegisterEmail.route) {
            RegisterEmailScreen { email ->
                navController.navigate(Screen.RegisterVerify.route)
            }
        }

        composable(Screen.RegisterVerify.route) {
            RegisterVerifyScreen(
                email = "user@email.com",
                onVerifySuccess = {
                    navController.navigate(Screen.RegisterComplete.route)
                },
                onResendCode = { /* resend logic */ }
            )
        }

        composable(Screen.RegisterComplete.route) {
            RegisterCompleteScreen {
                navController.navigate(Screen.Login.route)
            }
        }

        composable(Screen.ForgotPassword.route) {
            ForgotEmailScreen { email ->
                navController.navigate(Screen.ForgotPasswordVerify.route)
            }
        }

        composable(Screen.ForgotPasswordVerify.route) {
            ForgotVerifyScreen(
                email = "user@email.com",
                onVerifySuccess = {
                    navController.navigate(Screen.ForgotPasswordReset.route)
                },
                onResendCode = { /* resend logic */ }
            )
        }

        composable(Screen.ForgotPasswordReset.route) {
            ForgotResetPasswordScreen { newPassword ->
                navController.navigate(Screen.Login.route)
            }
        }
        composable(Screen.Main.route) {
            MainScreen()
        }

//        composable(Screen.HomeScreen.route) { HomeScreen() }
//        composable(Screen.SquareScreen.route) { SquareScreen(navController) }
//        composable(Screen.PostDetail.route) { GetPostDetail()}
//        composable(Screen.AddScreen.route) { AddScreen() }
//        composable(Screen.TodoScreen.route) { TodoScreen(
//            currentUser = LocalUser.user1,
//            navigateToDetail = { eventId ->
//                navController.navigate(Screen.EventDetail.createRoute(eventId))
//            }
//        ) }
//        composable(
//            route = Screen.EventDetail.route,
//            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
//
//            val event = LocalEvent.events.find { it.eventID == eventId }
//            val currentUser = LocalUser.user1
//
//            if (event != null && currentUser != null) {
//                EventDetailScreen(
//                    event = event,
//                    currentUser = currentUser,
//                    onNavigateBack = { navController.popBackStack() },
//                    navController = navController,
//                )
//            }
//        }
//        composable(Screen.RequestsList.route) {
//            RequestsList(
//                onAvatarClick = { userId ->
//                    // navController.navigate(Screen.UserProfile.createRoute(userId))
//                },navController = navController
//            )
//        }
//        composable(Screen.ProfileScreen.route) { ProfileScreen() }
    }
}