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
import com.example.mysquad.workManager.dailyReminder
import com.google.android.libraries.places.api.Places


class MainActivity : ComponentActivity() {

    @RequiresApi(64)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, "AIzaSyCoKwYA8ZnmXRFhKBiysdAxH7wKaTYj5mM")
        dailyReminder(applicationContext)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1001
        )

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
                    authViewModel = authViewModel,
                )
            }
        }
    }
}


