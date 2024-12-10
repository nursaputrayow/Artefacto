package com.nursaputrayow.artefacto.screen

import androidx.compose.material3.TextButton
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerificationCodeScreen(
    onSubmitCode: (String) -> Unit,
    onResendCode: () -> Unit
) {
    var code by remember { mutableStateOf("") }
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
                text = "Enter Verification Code",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                repeat(6) { index ->
                    BasicTextField(
                        value = code.getOrNull(index)?.toString() ?: "",
                        onValueChange = {
                            if (it.length <= 1) {
                                val updatedCode = code.toMutableList()
                                if (it.isNotEmpty()) {
                                    updatedCode[index] = it.first()
                                } else if (index < code.length) {
                                    updatedCode.removeAt(index)
                                }
                                code = updatedCode.joinToString("")
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .border(1.dp, Color.Gray)
                            .background(Color.White)
                            .padding(8.dp),
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (code.length == 6) {
                        errorMessage = null
                        onSubmitCode(code)
                    } else {
                        errorMessage = "Please enter a 6-digit code"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onResendCode) {
                Text("Resend Code", fontSize = 16.sp)
            }
        }
    }
}