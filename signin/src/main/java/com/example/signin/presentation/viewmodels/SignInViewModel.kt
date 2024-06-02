package com.example.signin.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signin.domain.entities.ModalResult
import com.example.signin.domain.entities.SiweResult
import com.example.signin.domain.usecases.AuthenticateSIWE
import com.example.signin.domain.usecases.GetModalEvents
import com.example.signin.presentation.uistates.WalletConnectUiState
import com.example.signin.presentation.uistates.WalletConnectUiState.*
import com.walletconnect.web3.modal.client.Web3Modal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getModalEvents: GetModalEvents,
    private val authenticateSIWE: AuthenticateSIWE,
    private val ioDispatcher: CoroutineDispatcher) : ViewModel() {
    private val _walletConnectState = MutableStateFlow<WalletConnectUiState>(NoState)
    val walletConnectState: StateFlow<WalletConnectUiState> = _walletConnectState

    init { getModalEvents() }

    private fun getModalEvents() = viewModelScope.launch(ioDispatcher) {
        _walletConnectState.value = Loading
        getModalEvents.invoke().collect { result ->
            _walletConnectState.value  = when (result) {
                 ModalResult.UserShouldSIWE -> ShowSIWE
                is ModalResult.UserConnected -> {GoToHomeScreen}
                is ModalResult.UserDisconnected -> ShowConnectWallet
                is ModalResult.Error -> ShowError(result.reason)
                else -> NoState
            }
        }
    }

    fun swieButtonClicked() = viewModelScope.launch(ioDispatcher) {
         _walletConnectState.value = Loading
         authenticateSIWE.invoke().collect {siweResult ->
                _walletConnectState.value = when (siweResult) {
                    is SiweResult.Success -> GoToHomeScreen
                    is SiweResult.Error -> ShowError(siweResult.message)
                }
         }
    }

    fun siweCancel() = viewModelScope.launch(ioDispatcher) {
            _walletConnectState.value = Loading
            Web3Modal.disconnect(
                onSuccess = {
                    viewModelScope.launch {
                        _walletConnectState.emit(
                            ShowConnectWallet
                        )
                    }
                },
                onError = { throwable: Throwable ->
                    println("USER DISCONNECT ERROR: ${throwable.message}")
                }
            )
    }
}