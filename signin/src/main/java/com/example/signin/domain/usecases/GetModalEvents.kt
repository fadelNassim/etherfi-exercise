package com.example.signin.domain.usecases

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.repositories.Web3ModalRepository
import com.example.signin.domain.entities.ModalResult
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetModalEvents @Inject constructor(private val repository: Web3ModalRepository){
    suspend fun invoke() = repository.fetchModalResponse().map { response ->
        when (response) {
            is ModalResponse.SessionApproved -> ModalResult.FirstConnectionSuccess
            is ModalResponse.ConnectionAvailable -> ModalResult.ConnectionAvailable
            is ModalResponse.RejectedSession,
            is ModalResponse.ConnectionNotAvailable,
            is ModalResponse.DeletedSessionError,
            is ModalResponse.SessionAuthenticateError,
            is ModalResponse.Error -> ModalResult.Error("Oops! Something went wrong, try again later.")
            else -> {}
        }
    }
}