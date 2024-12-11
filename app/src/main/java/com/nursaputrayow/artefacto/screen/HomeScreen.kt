package com.nursaputrayow.artefacto.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button

import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}