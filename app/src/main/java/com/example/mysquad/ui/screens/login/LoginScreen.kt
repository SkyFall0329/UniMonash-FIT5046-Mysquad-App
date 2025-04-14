package com.example.mysquad.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mysquad.R
import com.example.mysquad.ui.theme.ThemeMode
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreenWithAnimation(
    onLoginClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onThemeChange: (ThemeMode) -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val visibleState = remember { MutableTransitionState(false).apply { targetState = true } }

    val themeOptions = listOf("Light", "System", "Dark")
    val themeIcons = listOf(Icons.Default.WbSunny, Icons.Default.Settings, Icons.Default.DarkMode)
    var selectedIndex by remember { mutableIntStateOf(1) } // 默认选中“System”

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GradientArtText1()
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visibleState = visibleState,
                enter = fadeIn(tween(1000)) + scaleIn(initialScale = 0.6f, animationSpec = tween(1000)),
                exit = fadeOut()
            ) {
                GradientArtText2()
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.VpnKey, contentDescription = "Password Icon")
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onGoogleSignInClick,
                border = BorderStroke(1.dp, Color(0xFF4285F4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Sign-In",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sign in with Google", color = Color(0xFF4285F4),fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onForgotPasswordClick) {
                    Text("Forgot Password?", fontSize = 15.sp)
                }
                TextButton(onClick = onSignUpClick) {
                    Text("Sign Up", fontSize = 15.sp)
                }
            }
        }

        // 🌙 主题切换器浮动底部
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp), // ✅ 控制间距
            verticalAlignment = Alignment.CenterVertically
        ){
            themeOptions.forEachIndexed { index, label ->
                val isSelected = selectedIndex == index
                val bgColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF1565C0) else Color.Transparent,
                    animationSpec = tween(durationMillis = 300)
                )
                val contentColor = if (isSelected) Color.White else Color.Gray
                val borderColor = if (isSelected) Color(0xFF1565C0) else Color.Gray

                OutlinedButton(
                    onClick = {
                        selectedIndex = index
                        onThemeChange(
                            when (label) {
                                "Light" -> ThemeMode.LIGHT
                                "Dark" -> ThemeMode.DARK
                                else -> ThemeMode.SYSTEM
                            }
                        )
                    },
                    modifier = Modifier.size(52.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = bgColor,
                        contentColor = contentColor
                    ),
                    border = BorderStroke(1.dp, borderColor),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(imageVector = themeIcons[index], contentDescription = label, tint = contentColor)
                }
            }
        }
    }
}

@Composable
fun ThemeModeSelector(
    modifier: Modifier = Modifier,
    onModeSelected: (ThemeMode) -> Unit
) {
    val options = listOf("Light", "System", "Dark")
    val icons = listOf(Icons.Default.WbSunny, Icons.Default.Settings, Icons.Default.DarkMode)
    var selectedIndex by remember { mutableStateOf(1) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { index, label ->
            val isSelected = selectedIndex == index
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) Color(0xFF1565C0) else Color.Transparent,
                animationSpec = tween(durationMillis = 300)
            )
            val contentColor = if (isSelected) Color.White else Color.Gray
            val borderColor = if (isSelected) Color(0xFF1565C0) else Color.Gray

            OutlinedButton(
                onClick = {
                    selectedIndex = index
                    onModeSelected(
                        when (label) {
                            "Light" -> ThemeMode.LIGHT
                            "Dark" -> ThemeMode.DARK
                            else -> ThemeMode.SYSTEM
                        }
                    )
                },
                modifier = Modifier.size(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = bgColor,
                    contentColor = contentColor
                ),
                border = BorderStroke(1.dp, borderColor),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = icons[index],
                    contentDescription = label,
                    tint = contentColor
                )
            }
        }
    }
}

@Composable
fun GradientArtText1() {
    BasicText(
        text = "MySquad",
        style = TextStyle(
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF42A5F5), Color(0xFF7E57C2))
            )
        )
    )
}

@Composable
fun GradientArtText2() {
    BasicText(
        text = "Let’s Get Moving!",
        style = TextStyle(
            fontSize = 45.sp,
            fontWeight = FontWeight.Light,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF7E57C2), Color(0xFF42A5F5))
            )
        )
    )
}

@Composable
fun BlurredBackgroundScreen(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_bright),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.3f)) // 模拟磨砂
        )

        content() // 主界面内容
    }
}