package com.nursaputrayow.artefacto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nursaputrayow.artefacto.screen.LoginScreen
import com.nursaputrayow.artefacto.viewmodel.SplashScreenViewModel
import com.nursaputrayow.artefacto.screen.OnboardingScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import com.nursaputrayow.artefacto.screen.RegisterScreen
import com.nursaputrayow.artefacto.ui.theme.ArtefactoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtefactoTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Splash
    ) {
        composable(AppRoutes.Splash) {
            SplashScreenContent(
                onNavigateToOnboarding = { navController.navigate(AppRoutes.Onboarding) }
            )
        }
        composable(AppRoutes.Onboarding) {
            OnboardingScreen(
                onNavigateToLogin = { navController.navigate(AppRoutes.Login) }
            )
        }
        composable(AppRoutes.Login) {
            LoginScreen()
        }
        composable(AppRoutes.Register) {
            RegisterScreen()
        }
    }
}

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
    androidx.compose.material3.Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo"
        )
    }
}
