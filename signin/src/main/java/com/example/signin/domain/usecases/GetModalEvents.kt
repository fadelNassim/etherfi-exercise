package com.example.signin.domain.usecases

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.repositories.Web3ModalRepository
import com.example.signin.domain.entities.ModalResult
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetModalEvents @Inject constructor(private val repository: Web3ModalRepository){
    suspend fun invoke() = repository.fetchModalResponse().map { response ->
        when (response) {
            is ModalResponse.SessionApproved -> ModalResult.FirstConnectionSuccess(message = "First Connection Successful")
            is ModalResponse.ConnectionState -> ModalResult.ReconnectionSuccess("Reconnection Successful")
            is ModalResponse.UpdatedSession,
            is ModalResponse.SessionEvent,
            is ModalResponse.Event,
            is ModalResponse.DeletedSessionSuccess,
            is ModalResponse.Session,
            is ModalResponse.SessionRequestResponse,
            is ModalResponse.SessionAuthenticateResult,
            is ModalResponse.ExpiredProposal,
            is ModalResponse.ExpiredRequest,
           -> ModalResult.Success("Operation was successful")

            is ModalResponse.RejectedSession,
            is ModalResponse.DeletedSessionError,
            is ModalResponse.SessionAuthenticateError,
            is ModalResponse.Error -> ModalResult.Error("Oops! Something went wrong.")
            is ModalResponse.Unknown -> {}
        }
    }
}