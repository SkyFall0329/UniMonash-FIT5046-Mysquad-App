package com.example.mysquad.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Login : Screen("login_screen")
    object RegisterEmail : Screen("register_email")
    object RegisterVerify : Screen("register_code_verify")
    object RegisterComplete : Screen("register_complete")
    object ForgotPassword : Screen("forgot_password_screen")
    object ForgotPasswordEmail : Screen("forgot_password_email")
    object ForgotPasswordVerify : Screen("forgot_password_verify")
    object ForgotPasswordReset : Screen("forgot_password_reset")

    object MainGraph : Screen("main_graph")
    object HomeScreen : Screen("home_screen")
    object SquareScreen : Screen("square_screen")
    object PostDetail : Screen("post_detail/{eventId}") { fun postId(eventId: String) = "post_detail/$eventId" }
    object AddScreen : Screen("add_screen")
    object TodoScreen : Screen("todo_screen")
    object ProfileScreen : Screen("profile_screen")
    object EventDetail {
        const val base = "eventDetail"
        const val arg  = "eventId"
        const val route = "$base/{$arg}"
        fun createRoute(eventId: String) = "$base/$eventId"
    }
    object RequestsList : Screen("requests_list/{eventId}"){ fun createRoute(eventId: String): String = "requests_list/$eventId" }
    object UserProfile : Screen("user_profile/{userId}") { fun createRoute(userId: String): String = "user_profile/$userId" }
}