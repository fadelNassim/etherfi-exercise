package com.example.walletconnect.usecases.entities

sealed class DisconnectUserResult {
    data object UserDisconnected : DisconnectUserResult()
    data object CouldNotDisconnect : DisconnectUserResult()
}