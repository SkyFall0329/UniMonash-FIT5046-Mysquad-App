package com.example.mysquad.ui.screens.authScreens.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun RegisterVerifyScreen(
    email: String,
    onVerifySuccess: () -> Unit,
    onResendCode: (String) -> Unit
) {
    val codeLength = 6
    var code by remember { mutableStateOf("") }
    var countdown by remember { mutableStateOf(60) }

    // 启动倒计时
    LaunchedEffect(key1 = countdown) {
        if (countdown > 0) {
            delay(1000L)
            countdown--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter Verification Code", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary,fontWeight = FontWeight.Bold,)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Code sent to $email", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(32.dp))

        SixDigitCodeInput(
            code = code,
            onCodeChange = { code = it }
        )


        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = {
                if (countdown == 0) {
                    countdown = 60
                    onResendCode(email)
                }
            },
            enabled = countdown == 0
        ) {
            Text(
                if (countdown == 0) "Resend Code" else "Resend in ${countdown}s",
                color = if (countdown == 0) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onVerifySuccess,
            enabled = code.length == codeLength,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify")
        }
    }
}

@Composable
fun SixDigitCodeInput(
    code: String,
    onCodeChange: (String) -> Unit
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (i in 0 until 6) {
            val char = code.getOrNull(i)?.toString() ?: ""

            OutlinedTextField(
                value = char,
                onValueChange = { input ->
                    if (input.isNotEmpty() && code.length >= 6) return@OutlinedTextField

                    when {
                        input.length > 1 && input.all { it.isDigit() } -> {
                            val newCode = input.take(6)
                            onCodeChange(newCode)
                        }

                        input.length == 1 && input.all { it.isDigit() } -> {
                            val newCode = buildString {
                                append(code.take(i))
                                append(input)
                                append(code.drop(i + 1))
                            }.take(6)
                            onCodeChange(newCode)

                            if (i < 5) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        }

                        input.isEmpty() -> {
                            val newCode = buildString {
                                append(code.take(i))
                                append("")
                                append(code.drop(i + 1))
                            }.take(6)
                            onCodeChange(newCode)

                            if (i > 0) {
                                focusRequesters[i - 1].requestFocus()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .width(48.dp)
                    .height(56.dp)
                    .focusRequester(focusRequesters[i]),

                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}