package com.example.home.presentation.uistates

sealed class HomeUiState {
    data object GoToSignIn : HomeUiState()
    data object ShowTryAgain : HomeUiState()
    data class ShowHomeContent(
        val walletAddress: String?
    ) : HomeUiState()

    data object Loading : HomeUiState()
}