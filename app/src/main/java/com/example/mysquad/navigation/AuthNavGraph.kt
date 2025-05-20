package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mysquad.ViewModel.AuthUiState
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ui.screens.authScreens.ResetPasswordScreen
import com.example.mysquad.ui.screens.authScreens.LoginScreenWithAnimation
import com.example.mysquad.ui.screens.mainScreens.NavigationBar.MainScreen
import com.example.mysquad.ui.screens.authScreens.register.RegisterCompleteScreen
import com.example.mysquad.ui.screens.authScreens.register.RegisterEmailScreen
import com.example.mysquad.ui.screens.authScreens.register.RegisterVerifyScreen
import com.example.mysquad.ui.theme.ThemeMode
import kotlinx.coroutines.delay

@RequiresApi(64)
fun NavGraphBuilder.AuthNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel
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
            authViewModel.setTempEmail(email.trim())
            navController.navigate(Screen.RegisterComplete.route)
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
        val snackbarHost = remember{ SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHost) }
        ) { contentPadding ->   // 👈 catch this here
            RegisterCompleteScreen(
                modifier = Modifier.padding(contentPadding)  // 👈 apply it here
            ) { username, password ->
                authViewModel.register(authViewModel.tempEmail, password, username)
            }
        }
        // Listen for success/error
        LaunchedEffect(authViewModel.uiState) {
            when (authViewModel.uiState) {
                is AuthUiState.Success -> {
                    // 1) show the verification-sent message
                    snackbarHost.showSnackbar("A verification email has been sent to your email address")
                    // 2) wait briefly so the user sees it
                    delay(2000)
                    // 3) then pop back to Login (and clear the back stack)
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.RegisterEmail.route) { inclusive = true }
                    }
                }
                is AuthUiState.Error -> {
                    snackbarHost.showSnackbar((authViewModel.uiState as AuthUiState.Error).message)
                }
                else -> { /* idle or loading—no action */ }
            }
        }
    }
    /** ---------- FORGOT-PASSWORD FLOW ---------- **/
    composable(Screen.ForgotPassword.route) {
        ResetPasswordScreen(
            navController = navController,
            viewModel = authViewModel
        )
    }

    composable(Screen.MainGraph.route) {
        MainScreen(
            authViewModel = authViewModel,
            onThemeChange = onThemeChange,
            rootNavController = navController
        )
    }
}