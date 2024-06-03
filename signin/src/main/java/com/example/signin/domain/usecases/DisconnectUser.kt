package com.example.signin.domain.usecases

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.repositories.Web3ModalRepository
import com.example.signin.domain.entities.DisconnectUserResult
import com.example.signin.domain.entities.DisconnectUserResult.CouldNotDisconnect
import com.example.signin.domain.entities.DisconnectUserResult.UserDisconnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DisconnectUser @Inject constructor(private val repository: Web3ModalRepository) {
     fun invoke(): Flow<DisconnectUserResult> = repository.disconnectUser().map {
        when (it) {
            is ModalResponse.DisconnectSucces -> UserDisconnected
            else -> CouldNotDisconnect
        }
    }
}