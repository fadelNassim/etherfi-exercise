package com.example.signin.presentation.uistates

import com.example.signin.domain.entities.ErrorReason

sealed class SignInUiState {
    data object ShowSIWE : SignInUiState()
    data object ShowConnectWallet : SignInUiState()
    data class ShowError(val reason: ErrorReason) : SignInUiState()
    data class ShowTryAgain(val reason: ErrorReason) : SignInUiState()
    data object Loading : SignInUiState()
    data object NoState : SignInUiState()
    data object GoToHomeScreen : SignInUiState()
}