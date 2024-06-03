package com.example.signin.domain.usecases

import com.example.walletconnect.data.repositories.SiweRepository
import com.example.walletconnect.data.repositories.Web3ModalRepository
import javax.inject.Inject

class GetUserSession @Inject constructor(private val modalRepository: Web3ModalRepository, private val siweRepository: SiweRepository) {
    val isFullySignedIn = siweRepository.isSiweAuthenticated() && modalRepository.hasAccount()
    val needSiwe = siweRepository.isSiweAuthenticated().not() && modalRepository.hasAccount()
    val hasAccount = modalRepository.hasAccount()
}
