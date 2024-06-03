package com.example.walletconnect.usecases.usecases

import com.example.walletconnect.data.models.ModalResponse
import com.example.walletconnect.data.repositories.Web3ModalRepository
import com.example.walletconnect.usecases.entities.DisconnectUserResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DisconnectUser @Inject constructor(private val repository: Web3ModalRepository) {
    fun invoke(): Flow<DisconnectUserResult> = repository.disconnectUser().map { response ->
        when (response) {
            is ModalResponse.DisconnectSucces -> DisconnectUserResult.UserDisconnected
            else -> DisconnectUserResult.CouldNotDisconnect
        }
    }
}