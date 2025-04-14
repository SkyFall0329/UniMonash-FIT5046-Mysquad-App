package com.example.mysquad.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object RegisterEmail : Screen("register_email")
    object RegisterVerify : Screen("register_code_verify")
    object RegisterComplete : Screen("register_complete")
    object ForgotPassword : Screen("forgot_password_screen")
}