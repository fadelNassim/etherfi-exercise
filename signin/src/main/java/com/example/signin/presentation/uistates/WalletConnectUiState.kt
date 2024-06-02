package com.example.signin.presentation.uistates

sealed class WalletConnectUiState {
    data object FirstConnectionSuccess : WalletConnectUiState()
    data object ShowConnectWallet: WalletConnectUiState()
    data class ShowError(val message: String) : WalletConnectUiState()
    data object Loading : WalletConnectUiState()
    data object NoState : WalletConnectUiState()
    data object GoToHomeScreen : WalletConnectUiState()
}