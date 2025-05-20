package com.example.mysquad

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mysquad.ViewModel.AuthViewModel
import com.example.mysquad.ui.theme.MySquadTheme
import com.example.mysquad.ui.theme.ThemeMode
import android.Manifest
import com.example.mysquad.navigation.RootNavGraph


class MainActivity : ComponentActivity() {

    @RequiresApi(64) // 你的项目如果确实需要API 64，可保留；否则可能是误加
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1001
            )
        }

        setContent {
            var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }

            val isDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            MySquadTheme(
                dynamicColor = false,
                darkTheme = isDarkTheme
            ) {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()

                RootNavGraph(
                    navController = navController,
                    onThemeChange = { themeMode = it },
                    authViewModel = authViewModel
                )
            }
        }
    }
}


