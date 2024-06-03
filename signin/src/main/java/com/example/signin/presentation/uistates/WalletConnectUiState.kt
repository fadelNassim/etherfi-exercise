package com.example.signin.presentation.uistates

import com.example.signin.domain.entities.ErrorReason

sealed class WalletConnectUiState {
    data object ShowSIWE : WalletConnectUiState()
    data object ShowConnectWallet : WalletConnectUiState()
    data class ShowError(val reason: ErrorReason) : WalletConnectUiState()
    data class ShowTryAgain(val reason: ErrorReason) : WalletConnectUiState()
    data object Loading : WalletConnectUiState()
    data object NoState : WalletConnectUiState()
    data object GoToHomeScreen : WalletConnectUiState()
}