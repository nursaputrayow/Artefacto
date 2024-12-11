package com.nursaputrayow.artefacto.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onUpdate: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Name Field
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Gray)
                    .padding(12.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (name.isEmpty()) {
                            Text("Name", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Gray)
                    .padding(12.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (email.isEmpty()) {
                            Text("Email", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Gray)
                    .padding(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    Box {
                        if (password.isEmpty()) {
                            Text("Password", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Error Message
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Update Button
            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        errorMessage = "All fields are required"
                    } else {
                        errorMessage = null
                        onUpdate(name, email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
        }
    }
}