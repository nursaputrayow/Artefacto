package com.nursaputrayow.artefacto.screen

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import com.nursaputrayow.artefacto.viewmodel.SplashScreenViewModel
import com.nursaputrayow.artefacto.R

@Composable
fun SplashScreenContent(
    viewModel: SplashScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToOnboarding: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        SplashScreen()
    } else {
        LaunchedEffect(Unit) {
            onNavigateToOnboarding()
        }
    }
}

@Composable
fun SplashScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo"
        )
    }
}