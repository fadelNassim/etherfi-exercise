package com.example.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.domain.usecases.GetWalletAddress
import com.example.walletconnect.domain.usecases.IsNetworkAvailable
import com.example.home.presentation.uistates.HomeUiState
import com.example.home.presentation.uistates.HomeUiState.*
import com.example.walletconnect.domain.entities.DisconnectUserResult
import com.example.walletconnect.domain.usecases.DisconnectUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val disconnectUser: DisconnectUser,
    private val getWalletAddress: GetWalletAddress,
    private val isNetworkAvailable: IsNetworkAvailable,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _homeUiState =
        MutableStateFlow<HomeUiState>(ShowHomeContent(getWalletAddress.invoke()))
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private suspend fun handleDisconnect() {
        disconnectUser.invoke().collect { result ->
            _homeUiState.value = when (result) {
                DisconnectUserResult.UserDisconnected -> GoToSignIn
                else -> ShowTryAgain
            }
        }
    }

    fun onDisconnectClick() = viewModelScope.launch(ioDispatcher) {
        _homeUiState.value = Loading
        if (isNetworkAvailable.invoke().not()) {
            _homeUiState.value = ShowTryAgain
        } else {
           handleDisconnect()
        }
    }
}