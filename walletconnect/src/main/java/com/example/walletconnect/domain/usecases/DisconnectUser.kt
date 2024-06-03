package com.example.walletconnect.domain.usecases

import com.example.walletconnect.data.models.ModalResponse
import com.example.walletconnect.data.repositories.SiweRepository
import com.example.walletconnect.data.repositories.Web3ModalRepository
import com.example.walletconnect.domain.entities.DisconnectUserResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DisconnectUser @Inject constructor(
    private val repository: Web3ModalRepository,
    private val siweRepository: SiweRepository
) {
    fun invoke(): Flow<DisconnectUserResult> = repository.disconnectUser().map { response ->
        when (response) {
            is ModalResponse.DisconnectSucces -> {
                siweRepository.clearSiweAuthentication()
                return@map DisconnectUserResult.UserDisconnected
            }
            else -> DisconnectUserResult.CouldNotDisconnect
        }
    }
}