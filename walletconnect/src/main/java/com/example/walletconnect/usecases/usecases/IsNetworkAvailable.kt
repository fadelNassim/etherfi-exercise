package com.example.walletconnect.usecases.usecases

import com.example.walletconnect.data.repositories.Web3ModalRepository
import javax.inject.Inject

class IsNetworkAvailable @Inject constructor(private val repository: Web3ModalRepository) {
    fun invoke() = repository.isNetworkAvailable()
}