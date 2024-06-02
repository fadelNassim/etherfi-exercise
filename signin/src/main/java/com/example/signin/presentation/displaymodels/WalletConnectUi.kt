package com.example.signin.presentation.displaymodels

sealed class WalletConnectUi {
    data class Success(val message: String) : WalletConnectUi()
    data class ShowError(val message: String) : WalletConnectUi()
    data object Loading : WalletConnectUi()
    data object NoState : WalletConnectUi()
}