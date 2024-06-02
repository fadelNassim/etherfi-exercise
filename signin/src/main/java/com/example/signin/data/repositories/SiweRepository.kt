package com.example.signin.data.repositories

import com.example.signin.data.models.SiweResponse
import com.walletconnect.util.bytesToHex
import com.walletconnect.util.randomBytes
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SiweRepository @Inject constructor(ioDispatcher: CoroutineDispatcher) {
    private val scope = CoroutineScope(ioDispatcher)
    companion object {
        private const val AUTH_UNKNOWN_ERROR_MESSAGE = "SIWE Auth: Unknown error"
    }

    private val _result = MutableSharedFlow<SiweResponse>()
    val result: SharedFlow<SiweResponse> = _result

    fun authenticate() {
        val authenticateParams = Modal.Params.Authenticate(
            chains = Web3ModalChainsPresets.ethChains.values.map { it.id },
            domain = "sample.kotlin.dapp",
            uri = "https://web3inbox.com/all-apps",
            nonce = randomBytes(12).bytesToHex(),
            exp = null,
            nbf = null,
            statement = "Sign in with wallet.",
            requestId = null,
            resources = listOf(),
            methods = null,
            expiry = null
        )

        Web3Modal.authenticate(authenticateParams, onError = { error ->
            println(error.throwable.message ?: AUTH_UNKNOWN_ERROR_MESSAGE)
            scope.launch {
                _result.emit(
                    SiweResponse.Error(
                        message = error.throwable.message ?: AUTH_UNKNOWN_ERROR_MESSAGE
                    )
                )
            }
        }, onSuccess = { response ->
            println(response)
            scope.launch {
                _result.emit(SiweResponse.Success(message = response))
            }
        })
    }
}