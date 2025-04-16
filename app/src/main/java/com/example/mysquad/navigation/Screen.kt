package com.example.mysquad.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object RegisterEmail : Screen("register_email")
    object RegisterVerify : Screen("register_code_verify")
    object RegisterComplete : Screen("register_complete")
    object ForgotPassword : Screen("forgot_password_screen")
    object ForgotPasswordEmail : Screen("forgot_password_email")
    object ForgotPasswordVerify : Screen("forgot_password_verify")
    object ForgotPasswordReset : Screen("forgot_password_reset")
    object Main : Screen("main")
    object HomeScreen : Screen("home_screen")
    object SquareScreen : Screen("square_screen")
    object PostDetail : Screen("post_detail")
    object AddScreen : Screen("add_screen")
    object TodoScreen : Screen("todo_screen")
    object ProfileScreen : Screen("profile_screen")
    object EventDetail : Screen("event_detail/{eventId}") { fun createRoute(eventId: Int) = "event_detail/$eventId" }
    object RequestsList : Screen("requests_list")
    object UserProfile : Screen("user_profile/{userId}") { fun createRoute(userId: String): String = "user_profile/$userId" }
}