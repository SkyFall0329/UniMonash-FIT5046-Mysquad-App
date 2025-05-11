package com.example.mysquad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mysquad.firebase.AuthViewModel
import com.example.mysquad.navigation.AppNavGraph
import com.example.mysquad.ui.theme.MySquadTheme
import com.example.mysquad.ui.theme.ThemeMode


class MainActivity : ComponentActivity() {
    @RequiresApi(64)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }

            val isDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            MySquadTheme( dynamicColor = false,
                darkTheme = isDarkTheme){
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()
                AppNavGraph(
                    navController = navController,
                    onThemeChange = { themeMode = it }, // 👈 传下去
                    authViewModel = authViewModel
                )
            }
        }
    }
}