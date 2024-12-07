package com.nursaputrayow.artefacto

object AppRoutes {
    const val Splash = "splash"
    const val Onboarding = "onboarding"
    const val Login = "login"
    const val Register = "register"
    const val ForgotPassword = "ForgotPassword"
    const val ChangePassword = "ChangePassword"

    fun isValidRoute(route: String): Boolean {
        return route in listOf(Splash, Onboarding, Login, Register)
    }
}