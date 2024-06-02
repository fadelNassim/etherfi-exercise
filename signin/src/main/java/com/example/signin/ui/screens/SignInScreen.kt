package com.example.signin.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.signin.presentation.uistates.WalletConnectUiState
import com.example.signin.presentation.viewmodels.SignInViewModel
import com.walletconnect.web3.modal.ui.components.button.AccountButtonType
import com.walletconnect.web3.modal.ui.components.button.ConnectButtonSize
import com.walletconnect.web3.modal.ui.components.button.Web3Button
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.signin.domain.entities.ErrorReason

@Composable
fun SignInScreen(appPadding: PaddingValues, navController: NavController, goToHomeScreen: () -> Unit){
    val web3ModalState = rememberWeb3ModalState(navController = navController)
    val viewModel: SignInViewModel = hiltViewModel()
    val walletConnectState = viewModel.walletConnectState.collectAsState()

    Column(modifier = Modifier
        .padding(appPadding)
        .fillMaxWidth()
        .fillMaxHeight(), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally, verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center) {
        when (val state = walletConnectState.value) {
            is WalletConnectUiState.ShowSIWE -> {
                Row {
                    Button(onClick = {viewModel.siweCancel() }) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { viewModel.swieButtonClicked() }) {
                        Text(text = "Sign In with Ethereum")
                    }
                }
            }
            is WalletConnectUiState.ShowConnectWallet -> {
                Web3Button(
                    state = web3ModalState,
                    accountButtonType = AccountButtonType.NORMAL,
                    connectButtonSize = ConnectButtonSize.NORMAL
                )
            }
            is WalletConnectUiState.ShowError -> {
                val errorMessage = when (state.reason)  {
                    is ErrorReason.GenericError -> {stringResource(id = com.example.signin.R.string.generic_error)}
                    is ErrorReason.SessionAuthenticateError -> {
                        stringResource(id = com.example.signin.R.string.session_auth_error)
                    }
                    is ErrorReason.ExpiredProposal -> {
                        stringResource(id = com.example.signin.R.string.expired_proposal)
                    }
                    is ErrorReason.DeletedSessionError -> {
                        stringResource(id = com.example.signin.R.string.deleted_session_error)
                    }
                    is ErrorReason.RejectedSession -> {
                        stringResource(id = com.example.signin.R.string.rejected_session)
                    }
                    is ErrorReason.ConnectionNotAvailable -> {
                        stringResource(id = com.example.signin.R.string.connection_not_available)
                    }
                    is ErrorReason.FailedSiweAuthenticate -> {
                        stringResource(id = com.example.signin.R.string.session_auth_error)
                    }
                }
                Text(text = errorMessage)
            }
            is WalletConnectUiState.Loading -> {
                CircularProgressIndicator()
            }
            WalletConnectUiState.GoToHomeScreen -> {
                goToHomeScreen()
            }
            else -> {}
        }
    }
}