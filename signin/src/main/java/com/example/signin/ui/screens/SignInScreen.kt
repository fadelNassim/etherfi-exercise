package com.example.signin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.walletconnect.web3.modal.ui.components.button.AccountButton
import com.walletconnect.web3.modal.ui.components.button.AccountButtonType
import com.walletconnect.web3.modal.ui.components.button.ConnectButton
import com.walletconnect.web3.modal.ui.components.button.ConnectButtonSize
import com.walletconnect.web3.modal.ui.components.button.NetworkButton
import com.walletconnect.web3.modal.ui.components.button.Web3Button
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState
import com.walletconnect.web3.modal.ui.openWeb3Modal

@Composable
fun SignInScreen(appPadding: PaddingValues, navController: NavController) {
    val web3ModalState = rememberWeb3ModalState(navController = navController)
    Column(Modifier.padding(appPadding)) {
        Button(onClick = { println("OK"); navController.openWeb3Modal(shouldOpenChooseNetwork = true, onError = {
            println("WEB3 MODAL open: $it")
        })}) {
       
        }
        Web3Button(
            state = web3ModalState,
            accountButtonType = AccountButtonType.NORMAL,
            connectButtonSize = ConnectButtonSize.NORMAL
        )
        NetworkButton(state = web3ModalState)
        ConnectButton(
            state = web3ModalState,
            buttonSize = ConnectButtonSize.NORMAL
        )
        AccountButton(
            state = web3ModalState,
            accountButtonType = AccountButtonType.NORMAL
        )
    }
}