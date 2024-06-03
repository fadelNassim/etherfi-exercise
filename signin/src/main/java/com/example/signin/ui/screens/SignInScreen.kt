package com.example.signin.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.signin.presentation.uistates.SignInUiState
import com.example.signin.presentation.viewmodels.SignInViewModel
import com.walletconnect.web3.modal.ui.components.button.AccountButtonType
import com.walletconnect.web3.modal.ui.components.button.ConnectButtonSize
import com.walletconnect.web3.modal.ui.components.button.Web3Button
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Modifier

@Composable
fun SignInScreen(
    appPadding: PaddingValues,
    navController: NavController,
    goToHomeScreen: () -> Unit
) {
    val web3ModalState = rememberWeb3ModalState(navController = navController)
    val viewModel: SignInViewModel = hiltViewModel()
    val signInUiState = viewModel.signInUiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(appPadding)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        when (val state = signInUiState.value) {
            is SignInUiState.ShowSIWE -> {
                SiweScreen(
                    onCancelClick = {
                        viewModel.resetConnection()
                    },
                    onSiweClick = {
                        viewModel.swieButtonClicked()
                    },
                    onSiweWrongMessageClick = {
                        viewModel.siweWrongMessageButtonClicked()
                    }
                )
            }
            is SignInUiState.ShowConnectWallet -> {
                Web3Button(
                    state = web3ModalState,
                    accountButtonType = AccountButtonType.NORMAL,
                    connectButtonSize = ConnectButtonSize.NORMAL
                )
            }
            is SignInUiState.ShowError -> {
                ErrorScreen(state.reason)
            }
            is SignInUiState.Loading -> {
                CircularProgressIndicator()
            }
            is SignInUiState.GoToHomeScreen -> {
                goToHomeScreen()
            }
            is SignInUiState.ShowTryAgain -> {
                TryAgainScreen(state.reason) {
                    viewModel.resetConnection()
                }
            }
            else -> {}
        }
    }
}