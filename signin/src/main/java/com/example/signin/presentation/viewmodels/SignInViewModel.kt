package com.example.signin.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signin.domain.entities.ModalResult
import com.example.signin.domain.usecases.GetModalEvents
import com.example.signin.presentation.uistates.WalletConnectUiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getModalEvents: GetModalEvents,
    private val ioDispatcher: CoroutineDispatcher) : ViewModel() {
    private val _walletConnectUi = MutableStateFlow<WalletConnectUiStates>(WalletConnectUiStates.NoState)
    val walletConnectState: StateFlow<WalletConnectUiStates> = _walletConnectUi

    init {
        getModalEvents()
    }

    private fun getModalEvents() = viewModelScope.launch(ioDispatcher) {
        _walletConnectUi.value = WalletConnectUiStates.Loading
        getModalEvents.invoke().collect { result ->
            _walletConnectUi.value  = when (result) {
                is ModalResult.FirstConnectionSuccess -> WalletConnectUiStates.FirstConnectionSuccess
                is ModalResult.ReconnectionSuccess -> WalletConnectUiStates.ReconnectionSuccess(result.message)
                is ModalResult.Success -> WalletConnectUiStates.Success(result.message)
                is ModalResult.Error -> WalletConnectUiStates.ShowError(result.message)
                else -> WalletConnectUiStates.NoState
            }
        }
    }
}