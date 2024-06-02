package com.example.signin.domain.entities

sealed class ModalResult {
    data class FirstConnectionSuccess(val message: String) : ModalResult()
    data class ReconnectionSuccess(val message: String) : ModalResult()
    data class Success(val message: String) : ModalResult()
    data class Error(val message: String) : ModalResult()
}