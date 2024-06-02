package com.example.signin.data.repositories

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.models.ModalResponse.*
import com.example.walletconnect.ConnectWalletModalDelegate
import com.walletconnect.web3.modal.client.Modal.Model
import com.walletconnect.web3.modal.client.Modal.Model.SessionRequestResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Web3ModalRepository @Inject constructor(private val delegate: ConnectWalletModalDelegate) {
   suspend fun fetchModalResponse() = flow {
      delegate.event.collect { event ->
         emit(transformEventToModalResponse(event))
         println("Event: $event")
      }
   }

   private fun transformEventToModalResponse(event: Model): ModalResponse {
      return when (event) {
         is Model.ApprovedSession  -> { SessionApproved }
         is Model.RejectedSession -> {
            RejectedSession(event.topic, event.reason)
         }
         is Model.UpdatedSession -> {
            UpdatedSession(event.topic, event.namespaces)
         }
         is Model.SessionEvent -> {
            SessionEvent(event.name, event.data)
         }
         is Model.Event -> {
            Event(event.topic, event.name, event.data, event.chainId)
         }
         is Model.DeletedSession.Success -> {
            DeletedSessionSuccess(event.topic, event.reason)
         }
         is Model.DeletedSession.Error -> {
            DeletedSessionError(event.error)
         }
         is Model.Session -> {
            Session(event.pairingTopic, event.topic, event.expiry, event.namespaces, event.metaData)
         }
         is SessionRequestResponse -> {
            ModalResponse.SessionRequestResponse(event.topic, event.chainId, event.method, event.result)
         }
         is Model.SessionAuthenticateResponse.Result -> {
            SessionAuthenticateResult(event.id, event.cacaos, event.session)
         }
         is Model.SessionAuthenticateResponse.Error -> {
            SessionAuthenticateError(event.id, event.code, event.message)
         }
         is Model.ExpiredProposal -> {
            ExpiredProposal(event.pairingTopic, event.proposerPublicKey)
         }
         is Model.ExpiredRequest -> {
            ExpiredRequest(event.topic, event.id)
         }
         is Model.ConnectionState -> {
            ConnectionState(event.isAvailable)
         }
         is Model.Error -> {
            Error(event.throwable)
         }
         else -> Unknown
      }
   }
}