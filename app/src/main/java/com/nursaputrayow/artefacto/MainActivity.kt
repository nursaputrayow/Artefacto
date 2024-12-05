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
import com.nursaputrayow.artefacto.screen.ChangePasswordScreen
import com.nursaputrayow.artefacto.screen.ForgotPasswordScreen
import com.nursaputrayow.artefacto.screen.RegisterScreen
import com.nursaputrayow.artefacto.screen.SplashScreenContent
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
                onNavigateToOnboarding = {
                    navController.navigate(AppRoutes.Onboarding)
                    android.util.Log.d("Navigation", "SplashScreen")
                }
            )
        }
        composable(AppRoutes.Onboarding) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.Login)
                    android.util.Log.d("Navigation", "OnboardingScreen")
                }
            )
        }
        composable(AppRoutes.Login) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.Register)
                    android.util.Log.d("Navigation", "LoginScreen")
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AppRoutes.ForgotPassword)
                    android.util.Log.d("Navigation", "ForgotPassword")
                }
            )
        }
        composable(AppRoutes.Register) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.Login)
                    android.util.Log.d("Navigation", "RegisterScreen")
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AppRoutes.ForgotPassword)
                    android.util.Log.d("Navigation", "ForgotPassword")
                }
            )
        }
        composable(AppRoutes.ForgotPassword) {
            ForgotPasswordScreen(
                onNavigateToChangePassword = {
                    navController.navigate(AppRoutes.ChangePassword)
                    android.util.Log.d("Navigation", "ForgotPasswordScreen")
                },
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.Login)
                    android.util.Log.d("Navigation", "ChangePasswordScreen")
                }
            )
        }
        composable(AppRoutes.ChangePassword) {
            ChangePasswordScreen(
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.Login)
                    android.util.Log.d("Navigation", "ChangePasswordScreen")
                }
            )
        }
    }
}