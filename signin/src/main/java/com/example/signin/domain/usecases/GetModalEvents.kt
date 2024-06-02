package com.example.signin.domain.usecases

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.repositories.Web3ModalRepository
import com.example.signin.domain.entities.ModalResult
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetModalEvents @Inject constructor(private val repository: Web3ModalRepository){
    suspend fun invoke() = repository.fetchModalResponse().map {
        when (it) {
            is ModalResponse.SessionApproved,
            is ModalResponse.UpdatedSession,
            is ModalResponse.SessionEvent,
            is ModalResponse.Event,
            is ModalResponse.DeletedSessionSuccess,
            is ModalResponse.Session,
            is ModalResponse.SessionRequestResponse,
            is ModalResponse.SessionAuthenticateResult,
            is ModalResponse.ExpiredProposal,
            is ModalResponse.ExpiredRequest,
            is ModalResponse.ConnectionState -> ModalResult.Success("Operation was successful")

            is ModalResponse.RejectedSession,
            is ModalResponse.DeletedSessionError,
            is ModalResponse.SessionAuthenticateError,
            is ModalResponse.Error -> ModalResult.Error("An error occurred")

            else -> {}
        }
    }
}