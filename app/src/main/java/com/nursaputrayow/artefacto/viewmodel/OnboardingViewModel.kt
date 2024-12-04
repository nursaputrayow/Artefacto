package com.nursaputrayow.artefacto.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {
    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep

    fun nextStep() {
        if (_currentStep.value < 2) {
            _currentStep.value++
        }
    }

    fun resetSteps() {
        _currentStep.value = 0
    }
}