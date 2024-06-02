package com.example.signin.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.signin.presentation.uistates.WalletConnectUiStates
import com.example.signin.presentation.viewmodels.SignInViewModel
import com.walletconnect.web3.modal.ui.components.button.AccountButtonType
import com.walletconnect.web3.modal.ui.components.button.ConnectButtonSize
import com.walletconnect.web3.modal.ui.components.button.Web3Button
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.walletconnect.util.bytesToHex
import com.walletconnect.util.randomBytes
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets
import com.walletconnect.web3.modal.ui.components.button.ConnectButton

@Composable
fun SignInScreen(appPadding: PaddingValues, navController: NavController) {
    val web3ModalState = rememberWeb3ModalState(navController = navController)
    val viewModel: SignInViewModel = hiltViewModel()
    val walletConnectState = viewModel.walletConnectState.collectAsState()

    when (val state = walletConnectState.value) {
        is WalletConnectUiStates.FirstConnectionSuccess -> {
            val authenticateParams = Modal.Params.Authenticate(
                chains = Web3ModalChainsPresets.ethChains.values.map {it.id},
                domain = "sample.kotlin.dapp",
                uri = "https://web3inbox.com/all-apps",
                nonce = randomBytes(12).bytesToHex(),
                exp = null,
                nbf = null,
                statement = "Sign in with wallet.",
                requestId = null,
                resources = listOf(),
                methods = null,
                expiry = null
            )

            Web3Modal.authenticate(authenticateParams, onError = {
                println("SIWE ERROR")
            }, onSuccess = {
                println("SIWE SUCCESS")
            })
        }
        is WalletConnectUiStates.ReconnectionSuccess -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            Web3Button(
                state = web3ModalState,
                accountButtonType = AccountButtonType.NORMAL,
                connectButtonSize = ConnectButtonSize.NORMAL
            )
        }
        is WalletConnectUiStates.Success -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            Web3Button(
                state = web3ModalState,
                accountButtonType = AccountButtonType.NORMAL,
                connectButtonSize = ConnectButtonSize.NORMAL
            )
        }
        is WalletConnectUiStates.ShowError -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
        }
        is WalletConnectUiStates.Loading -> {
            CircularProgressIndicator()
        }
        WalletConnectUiStates.NoState -> {
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