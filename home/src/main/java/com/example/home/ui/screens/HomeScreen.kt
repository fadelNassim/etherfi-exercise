package com.example.home.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.ui.components.button.rememberWeb3ModalState

@Composable
fun HomeScreen(appPadding: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(appPadding)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Web3Modal.getAccount()?.address?.let { Text(text = it) }
        Button(onClick = {
            Web3Modal.disconnect(
                { navController.navigateUp() },
                { error: Throwable -> println("Error: $error") })
        }) {
            Text(text = "Disconnect")
        }
    }
}