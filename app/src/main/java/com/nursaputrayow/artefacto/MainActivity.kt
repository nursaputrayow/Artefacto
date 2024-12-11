package com.nursaputrayow.artefacto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nursaputrayow.artefacto.screen.LoginScreen
import com.nursaputrayow.artefacto.screen.MainScreen
import com.nursaputrayow.artefacto.screen.OnboardingScreen
import com.nursaputrayow.artefacto.screen.RegisterScreen
import com.nursaputrayow.artefacto.ui.theme.ArtefactoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtefactoTheme {
                ArtefactoApp()
            }
        }
    }
}

@Composable
fun ArtefactoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onLoginScreen = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onLoginScreen = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            LoginScreen(
                onRegisterScreen = {
                    navController.navigate("register") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onHomeScreen = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            MainScreen()
        }
    }
}