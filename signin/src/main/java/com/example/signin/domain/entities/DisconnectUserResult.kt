package com.example.signin.domain.entities

sealed class DisconnectUserResult {
    data object UserDisconnected : DisconnectUserResult()
    data object CouldNotDisconnect : DisconnectUserResult()
}