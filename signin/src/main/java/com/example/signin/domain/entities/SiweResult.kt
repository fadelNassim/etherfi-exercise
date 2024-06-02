package com.example.signin.domain.entities

sealed class SiweResult {
    data class Success(val message: String) : SiweResult()
    data class Error(val message: String) : SiweResult()
}