package com.example.mysquad.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.data.entityForTesting.jianhui.local.LocalEvent
import com.example.mysquad.data.entityForTesting.jianhui.local.LocalUser
import com.example.mysquad.data.entityForTesting.larry.UserProfile
import com.example.mysquad.ui.screens.login.LoginScreenWithAnimation
import com.example.mysquad.ui.screens.mainScreen.AddScreen.AddScreen
import com.example.mysquad.ui.screens.mainScreen.HomeScreen.HomeScreen
import com.example.mysquad.ui.screens.mainScreen.ProfileScreen.ProfileScreen
import com.example.mysquad.ui.screens.mainScreen.SquareScreen.GetPostDetail
import com.example.mysquad.ui.screens.mainScreen.SquareScreen.SquareScreen
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.EventDetailScreen
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.RequestsList
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.TodoScreen
import com.example.mysquad.ui.screens.mainScreen.TodoScreen.UserProfile
import com.example.mysquad.ui.screens.mainScreen.mainNavi.MainScreen
import com.example.mysquad.ui.theme.ThemeMode


@RequiresApi(64)
fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController,
    onThemeChange: (ThemeMode) -> Unit,
    authViewModel: AuthViewModel
) {
    composable(Screen.HomeScreen.route) {
        MainScreen(
            onThemeChange = onThemeChange,
            authViewModel = authViewModel,
            rootNavController = navController
        )
    }
}