package com.example.signin.presentation.uistates

import com.walletconnect.android.Core
import com.walletconnect.web3.modal.client.Modal

sealed class WalletConnectUiStates {
    data object FirstConnectionSuccess : WalletConnectUiStates()
    data class ReconnectionSuccess(val message: String) : WalletConnectUiStates()
    data class Success(val message: String) : WalletConnectUiStates()
    data class ShowError(val message: String) : WalletConnectUiStates()
    data object Loading : WalletConnectUiStates()
    data object NoState : WalletConnectUiStates()
}