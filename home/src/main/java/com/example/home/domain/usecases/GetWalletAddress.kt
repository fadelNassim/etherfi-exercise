package com.example.home.domain.usecases

import com.example.walletconnect.data.repositories.Web3ModalRepository
import javax.inject.Inject

class GetWalletAddress @Inject constructor(private val repository: Web3ModalRepository) {
    fun invoke(): String? {
        return repository.getWalletAddress()
    }
}