package com.example.signin.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walletconnect.domain.entities.DisconnectUserResult.UserDisconnected
import com.example.signin.domain.entities.ErrorReason
import com.example.signin.domain.entities.ModalResult
import com.example.signin.domain.entities.SiweResult
import com.example.signin.domain.usecases.AuthenticateSIWE
import com.example.walletconnect.domain.usecases.DisconnectUser
import com.example.signin.domain.usecases.GetModalEvents
import com.example.signin.domain.usecases.GetUserSession
import com.example.signin.presentation.uistates.SignInUiState
import com.example.signin.presentation.uistates.SignInUiState.*
import com.example.walletconnect.domain.usecases.IsNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val getModalEvents: GetModalEvents,
    private val getUserSession: GetUserSession,
    private val authenticateSIWE: AuthenticateSIWE,
    private val disconnectUser: DisconnectUser,
    private val isNetworkAvailable: IsNetworkAvailable,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _signInUiState = MutableStateFlow<SignInUiState>(NoState)
    val signInUiState: StateFlow<SignInUiState> = _signInUiState

    init {
        tryListenToModal()
    }

    private fun tryListenToModal() {
        if (isNetworkAvailable.invoke()) {
            handleRedirectWhenNetworkAvailable()
        } else {
            _signInUiState.value = ShowTryAgain(ErrorReason.ConnectionNotAvailable)
        }
    }

    private fun handleRedirectWhenNetworkAvailable() {
        when {
            getUserSession.isUserSIWEAuthenticated() && getUserSession.isUserWalletConnected() -> _signInUiState.value =
                GoToHomeScreen

            getUserSession.isUserWalletConnected() && getUserSession.isUserSIWEAuthenticated()
                .not() -> _signInUiState.value = ShowSIWE

            else -> getModalEvents()
        }
    }

    private fun getModalEvents() = viewModelScope.launch(ioDispatcher) {
        _signInUiState.value = ShowConnectWallet
        getModalEvents.invoke().collect { result ->
            _signInUiState.value = when (result) {
                ModalResult.UserShouldSIWE -> ShowSIWE
                is ModalResult.UserConnected -> {
                    GoToHomeScreen
                }

                is ModalResult.UserDisconnected -> ShowConnectWallet
                is ModalResult.Error -> ShowError(result.reason)
                else -> NoState
            }
        }
    }

    private fun collectSiweResult(isWrongMessage: Boolean = false) =
        viewModelScope.launch(ioDispatcher) {
            _signInUiState.value = Loading
            authenticateSIWE.invoke(isWrongMessage).collect { siweResult ->
                _signInUiState.value = when (siweResult) {
                    is SiweResult.Success -> GoToHomeScreen
                    is SiweResult.Error -> ShowTryAgain(siweResult.message)
                }
            }
        }

    fun swieButtonClicked() = collectSiweResult()

    fun siweWrongMessageButtonClicked() = collectSiweResult(isWrongMessage = true)

    fun tryResetConnection() = viewModelScope.launch(ioDispatcher) {
        if (isNetworkAvailable.invoke()) {
            _signInUiState.value = Loading
            disconnectUser.invoke().collect { result ->
                _signInUiState.value = when (result) {
                    UserDisconnected -> ShowConnectWallet
                    else -> ShowError(ErrorReason.GenericError)
                }
            }
        } else {
            _signInUiState.value = ShowTryAgain(ErrorReason.ConnectionNotAvailable)
        }
    }

    fun onTryAgainClicked(reason: ErrorReason) {
        if (reason == ErrorReason.FailedSiweAuthenticate) {
            tryResetConnection()
        } else {
            tryListenToModal()
        }
    }
}