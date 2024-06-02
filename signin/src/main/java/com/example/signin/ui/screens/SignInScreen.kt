package com.example.signin.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.signin.presentation.displaymodels.WalletConnectUi
import com.example.signin.presentation.viewmodels.SignInViewModel
import com.walletconnect.web3.modal.ui.components.button.AccountButtonType
import com.walletconnect.web3.modal.ui.components.button.ConnectButtonSize
import com.walletconnect.web3.modal.ui.components.button.Web3Button
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.walletconnect.web3.modal.ui.components.button.ConnectButton

@Composable
fun SignInScreen(appPadding: PaddingValues, navController: NavController) {
    val web3ModalState = rememberWeb3ModalState(navController = navController)
    val viewModel: SignInViewModel = hiltViewModel()
    val walletConnectState = viewModel.walletConnectState.collectAsState()

    when (val state = walletConnectState.value) {
        is WalletConnectUi.Success -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            Web3Button(
                state = web3ModalState,
                accountButtonType = AccountButtonType.NORMAL,
                connectButtonSize = ConnectButtonSize.NORMAL
            )
        }
        is WalletConnectUi.ShowError -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
        }
        is WalletConnectUi.Loading -> {
            CircularProgressIndicator()
        }
        WalletConnectUi.NoState -> {
            if (web3ModalState.isConnected.collectAsState(false).value) {

            } else {
                ConnectButton(
                    state = web3ModalState,
                    buttonSize = ConnectButtonSize.NORMAL
                )
            }
        }
    }
}