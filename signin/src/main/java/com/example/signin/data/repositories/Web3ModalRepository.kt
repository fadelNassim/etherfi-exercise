package com.example.signin.data.repositories

import com.example.signin.data.models.ModalResponse
import com.example.signin.data.models.ModalResponse.*
import com.example.walletconnect.ConnectWalletModalDelegate
import com.walletconnect.web3.modal.client.Modal.Model
import com.walletconnect.web3.modal.client.Modal.Model.SessionRequestResponse
import com.walletconnect.web3.modal.client.Web3Modal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class Web3ModalRepository @Inject constructor(
    private val delegate: ConnectWalletModalDelegate,
    ioDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(ioDispatcher)
    private val _disconnectEvents: MutableSharedFlow<ModalResponse> = MutableSharedFlow()
    val disconnectEvents: SharedFlow<ModalResponse> = _disconnectEvents.asSharedFlow()

    suspend fun fetchModalResponse() = flow {
        delegate.events.collect { event ->
            emit(transformEventToModalResponse(event))
            println("Event: $event")
        }
    }

    private fun transformEventToModalResponse(event: Model): ModalResponse {
        return when (event) {
            is Model.ApprovedSession -> {
                SessionApproved
            }

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
                Session(
                    event.pairingTopic, event.topic, event.expiry, event.namespaces, event.metaData
                )
            }

            is SessionRequestResponse -> {
                ModalResponse.SessionRequestResponse(
                    event.topic, event.chainId, event.method, event.result
                )
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
                getConnectionState(event.isAvailable)
            }

            is Model.Error -> {
                Error(event.throwable)
            }

            else -> Unknown
        }
    }

    private fun getConnectionState(available: Boolean): ModalResponse {
        return if (available) {
            ConnectionAvailable
        } else {
            ConnectionNotAvailable
        }
    }

     fun disconnectUser(): SharedFlow<ModalResponse> {
        Web3Modal.disconnect(onSuccess = {
            scope.launch {
                _disconnectEvents.emit(DisconnectSucces)
            }
        }, onError = { throwable: Throwable ->
            scope.launch {
                _disconnectEvents.emit(Error(throwable))
            }
        })

         return disconnectEvents
    }

    fun hasAccount(): Boolean {
        return Web3Modal.getAccount() != null
    }
}