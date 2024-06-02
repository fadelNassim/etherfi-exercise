package com.example.signin.domain.entities

sealed class ModalResult {
    data object FirstConnectionSuccess : ModalResult()
    data object ConnectionAvailable : ModalResult()
    data object ConnectionNotAvailable : ModalResult()
    data class Error(val message: String) : ModalResult()
}