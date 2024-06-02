package com.example.signin.domain.entities

sealed class ErrorReason {
    data object ConnectionNotAvailable : ErrorReason()
    data object RejectedSession : ErrorReason()
    data object DeletedSessionError : ErrorReason()
    data object SessionAuthenticateError : ErrorReason()
    data object ExpiredProposal : ErrorReason()
    data object GenericError : ErrorReason()
    data object FailedSiweAuthenticate : ErrorReason()
}

sealed class ModalResult {
    data class Error(val reason: ErrorReason) : ModalResult()
    data object UserShouldSIWE : ModalResult()
    data object UserConnected : ModalResult()
    data object UserDisconnected : ModalResult()
}