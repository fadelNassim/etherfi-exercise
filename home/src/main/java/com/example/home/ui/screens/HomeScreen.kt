package com.example.home.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.home.R
import com.example.home.presentation.uistates.HomeUiState
import com.example.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(appPadding: PaddingValues, goToSignIn: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeUiState = viewModel.homeUiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(appPadding)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = homeUiState.value) {
            is HomeUiState.GoToSignIn -> goToSignIn()
            is HomeUiState.ShowHomeContent -> {
                val walletAddress = state.walletAddress
                    ?: stringResource(id = R.string.failed_to_retrieve_account_address)
                HomeContent(walletAddress = walletAddress) {
                    viewModel.onDisconnectClick()
                }
            }
            HomeUiState.Loading -> CircularProgressIndicator()
            HomeUiState.ShowTryAgain -> {
                Text(text = stringResource(id = R.string.failed_to_disconnect))
                Button(onClick = { viewModel.onDisconnectClick() }) {
                    Text(text = stringResource(id = R.string.try_again_button))
                }
            }
        }
    }
}

@Composable
fun HomeContent(walletAddress: String, onDisconnectClick: () -> Unit) {
    Column {
        Text(
            text = walletAddress,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = {
            onDisconnectClick()
        }) {
            Text(text = "Disconnect")
        }
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    HomeContent("0x1234567890") {}
}