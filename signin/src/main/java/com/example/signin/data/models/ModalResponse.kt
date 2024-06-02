package com.example.signin.data.models

import com.walletconnect.android.Core
import com.walletconnect.web3.modal.client.Modal.Model

sealed class ModalResponse {
    data object SessionApproved : ModalResponse()
    data class RejectedSession(val topic: String, val reason: String) : ModalResponse()
    data class UpdatedSession(val topic: String, val namespaces: Map<String, Model.Namespace.Session>) : ModalResponse()
    data class SessionEvent(val name: String, val data: String) : ModalResponse()
    data class Event(val topic: String, val name: String, val data: String, val chainId: String) : ModalResponse()
    data class DeletedSessionSuccess(val topic: String, val reason: String) : ModalResponse()
    data class DeletedSessionError(val error: Throwable) : ModalResponse()
    data class Session(val pairingTopic: String, val topic: String, val expiry: Long, val namespaces: Map<String, Model.Namespace.Session>, val metaData: Core.Model.AppMetaData?) : ModalResponse()
    data class SessionRequestResponse(val topic: String, val chainId: String?, val method: String, val result: Model.JsonRpcResponse) : ModalResponse()
    data class SessionAuthenticateResult(val id: Long, val cacaos: List<Model.Cacao>, val session: Model.Session?) : ModalResponse()
    data class SessionAuthenticateError(val id: Long, val code: Int, val message: String) : ModalResponse()
    data class ExpiredProposal(val pairingTopic: String, val proposerPublicKey: String) : ModalResponse()
    data class ExpiredRequest(val topic: String, val id: Long) : ModalResponse()
    data class ConnectionState(val isAvailable: Boolean) : ModalResponse()
    data class Error(val throwable: Throwable) : ModalResponse()
    data object Unknown : ModalResponse()
}