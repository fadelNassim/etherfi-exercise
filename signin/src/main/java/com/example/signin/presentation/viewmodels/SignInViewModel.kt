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

        init {
            getModalEvents()
        }

    private val _walletConnectState = MutableStateFlow<WalletConnectUiState>(NoState)
    val walletConnectState: StateFlow<WalletConnectUiState> = _walletConnectState

    private fun getModalEvents() = viewModelScope.launch(ioDispatcher) {
        _walletConnectState.value = Loading
        getModalEvents.invoke().collect { result ->
            _walletConnectState.value  = when (result) {
                is ModalResult.FirstConnectionSuccess -> FirstConnectionSuccess
                is ModalResult.ConnectionAvailable -> handleAvailableConnection()
                is ModalResult.ConnectionNotAvailable -> ShowError("Connection not available.")
                is ModalResult.Error -> ShowError(result.message)
                else -> NoState
            }
        }
    }

   private fun handleAvailableConnection() = if (Web3Modal.getAccount() != null) {
        GoToHomeScreen
    } else {
        ShowConnectWallet
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

    fun resetUi() {
        _walletConnectState.value = NoState
    }
}