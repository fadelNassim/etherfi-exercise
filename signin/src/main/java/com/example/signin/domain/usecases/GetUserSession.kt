package com.example.signin.domain.usecases

import com.example.walletconnect.data.repositories.SiweRepository
import com.example.walletconnect.data.repositories.Web3ModalRepository
import javax.inject.Inject

class GetUserSession @Inject constructor(val modalRepository: Web3ModalRepository, val siweRepository: SiweRepository) {
    fun isUserSIWEAuthenticated() = siweRepository.isSiweAuthenticated()
    fun isUserWalletConnected() = modalRepository.hasAccount()
}
