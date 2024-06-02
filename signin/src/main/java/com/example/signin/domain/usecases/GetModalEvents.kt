package com.example.signin.domain.usecases

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.repositories.SiweRepository
import com.example.signin.data.repositories.Web3ModalRepository
import com.example.signin.domain.entities.ErrorReason.*
import com.example.signin.domain.entities.ModalResult
import com.walletconnect.web3.modal.client.Web3Modal
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetModalEvents @Inject constructor(private val web3ModalRepository: Web3ModalRepository, private val siweRepository: SiweRepository) {
    private fun handleConnectionAvailable(): ModalResult {
        return when {
            Web3Modal.hasAccount().not() -> {
                siweRepository.clearSiweAuthentication()
                ModalResult.UserDisconnected
            }
            Web3Modal.hasAccount() && siweRepository.isSiweAuthenticated() -> ModalResult.UserConnected
            else -> ModalResult.UserShouldSIWE
        }
    }

    suspend fun invoke() = web3ModalRepository.fetchModalResponse().map { response ->
        when (response) {
            is ModalResponse.SessionApproved -> ModalResult.UserShouldSIWE
            is ModalResponse.ConnectionAvailable -> handleConnectionAvailable()
            is ModalResponse.RejectedSession -> ModalResult.Error(reason = RejectedSession)
            is ModalResponse.ConnectionNotAvailable -> ModalResult.Error(reason = ConnectionNotAvailable)
            is ModalResponse.DeletedSessionError -> ModalResult.Error(reason = DeletedSessionError)
            is ModalResponse.SessionAuthenticateError -> ModalResult.Error(reason = SessionAuthenticateError)
            is ModalResponse.ExpiredProposal -> ModalResult.Error(reason = ExpiredProposal)
            is ModalResponse.Error -> ModalResult.Error(reason = GenericError)
            else -> {}
        }
    }

    private fun Web3Modal.hasAccount(): Boolean {
        return getAccount() != null
    }
}