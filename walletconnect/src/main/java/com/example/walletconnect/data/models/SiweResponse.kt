package com.example.walletconnect.data.models

sealed class SiweResponse {
    data class Success(val message: String) : SiweResponse()
    data class Error(val message: String) : SiweResponse()
}