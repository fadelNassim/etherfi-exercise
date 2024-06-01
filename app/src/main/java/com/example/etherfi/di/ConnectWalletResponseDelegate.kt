package com.example.etherfi.di

import com.walletconnect.web3.modal.client.Modal.Model
import com.walletconnect.web3.modal.client.Modal.Model.*
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.client.Web3Modal.ModalDelegate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ConnectWalletEventsDelegate (dispatcher: CoroutineDispatcher) : ModalDelegate {
    private val scope = CoroutineScope(dispatcher)
    private val _events: MutableSharedFlow<Model?> = MutableSharedFlow()
    val event: SharedFlow<Model?> =  _events.asSharedFlow()

    init {
        Web3Modal.setDelegate(this)
    }

    override fun onSessionApproved(approvedSession: ApprovedSession) {
        scope.launch {
           _events.emit(approvedSession)
        }
    }

    override fun onSessionRejected(rejectedSession: RejectedSession) {
        scope.launch {
           _events.emit(rejectedSession)
        }
    }

    override fun onSessionUpdate(updatedSession: UpdatedSession) {
        scope.launch {
           _events.emit(updatedSession)
        }
    }

    override fun onSessionEvent(sessionEvent: SessionEvent) {
        scope.launch {
           _events.emit(sessionEvent)
        }
    }

    override fun onSessionEvent(sessionEvent: Event) {
        scope.launch {
           _events.emit(sessionEvent)
        }
    }

    override fun onSessionDelete(deletedSession: DeletedSession) {
        scope.launch {
           _events.emit(deletedSession)
        }
    }

    override fun onSessionExtend(session: Session) {
        scope.launch {
           _events.emit(session)
        }
    }

    override fun onSessionRequestResponse(response: SessionRequestResponse) {
        scope.launch {
           _events.emit(response)
        }
    }

    override fun onSessionAuthenticateResponse(sessionUpdateResponse: SessionAuthenticateResponse) {
        TODO("Not yet implemented")
    }

    override fun onProposalExpired(proposal: ExpiredProposal) {
        scope.launch {
           _events.emit(proposal)
        }
    }

    override fun onRequestExpired(request: ExpiredRequest) {
        scope.launch {
           _events.emit(request)
        }
    }

    override fun onConnectionStateChange(state: ConnectionState) {
        scope.launch {
           _events.emit(state)
        }
    }

    override fun onError(error: Error) {
        println(error.throwable.stackTraceToString())
        scope.launch {
           _events.emit(error)
        }
    }
}