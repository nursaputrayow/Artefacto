package com.nursaputrayow.artefacto.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Splash Screen Logo",
                modifier = Modifier.size(200.dp).padding(16.dp)
            )
        }
    }
}