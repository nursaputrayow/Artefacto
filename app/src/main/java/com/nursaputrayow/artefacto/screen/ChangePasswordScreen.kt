package com.nursaputrayow.artefacto.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ChangePasswordScreen(onNavigateToLogin: () -> Unit) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Change Password",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            BasicTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray)
                    .padding(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(Modifier.padding(8.dp)) {
                        if (newPassword.isEmpty()) {
                            Text("New Password", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, Color.Gray)
                    .padding(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(Modifier.padding(8.dp)) {
                        if (confirmPassword.isEmpty()) {
                            Text("Confirm Password", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (newPassword.isBlank() || confirmPassword.isBlank()) {
                        errorMessage = "Please fill in both fields"
                    } else if (newPassword != confirmPassword) {
                        errorMessage = "Passwords do not match"
                    } else {
                        errorMessage = null
                        onNavigateToLogin()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Change Password")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text("Back to Login")
            }
        }
    }
}