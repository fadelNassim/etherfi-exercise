package com.example.signin.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.signin.domain.entities.ModalResult
import com.example.signin.domain.usecases.GetModalEvents
import com.example.signin.presentation.displaymodels.WalletConnectUi
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
    private val _walletConnectUi = MutableStateFlow<WalletConnectUi>(WalletConnectUi.NoState)
    val walletConnectState: StateFlow<WalletConnectUi> = _walletConnectUi

    init {
        getModalEvents()
    }

    private fun getModalEvents() = viewModelScope.launch(ioDispatcher) {
        _walletConnectUi.value = WalletConnectUi.Loading
        getModalEvents.invoke().collect { result ->
            _walletConnectUi.value  = when (result) {
                is ModalResult.Success -> WalletConnectUi.Success(result.message)
                is ModalResult.Error -> WalletConnectUi.ShowError(result.message)
                else -> WalletConnectUi.NoState
            }
        }
    }
}