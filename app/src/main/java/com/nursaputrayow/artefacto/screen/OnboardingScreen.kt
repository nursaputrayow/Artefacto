package com.nursaputrayow.artefacto.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nursaputrayow.artefacto.R
import com.nursaputrayow.artefacto.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: OnboardingViewModel = viewModel()
) {
    val currentStep by viewModel.currentStep.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnboardingContent(currentStep)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (currentStep == 2) {
                        onNavigateToLogin()
                    } else {
                        viewModel.nextStep()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (currentStep == 2) "Get Started" else "Next")
            }
        }
    }
}

@Composable
fun OnboardingContent(step: Int) {
    val onboardingData = remember {
        listOf(
            Triple("Discover the Past", "Explore the history of Prambanan Temple.", R.drawable.background),
            Triple("Scan Artifacts", "Scan and learn about historical treasures.", R.drawable.background),
            Triple("Book Your Ticket", "Plan your visit and book tickets.", R.drawable.background)
        )
    }

    Crossfade(targetState = step, animationSpec = androidx.compose.animation.core.tween(300)) { currentStep ->
        val (title, description, image) = onboardingData[currentStep]
        OnboardingStep(title, description, image)
    }
}

@Composable
fun OnboardingStep(
    title: String,
    description: String,
    image: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.height(250.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}