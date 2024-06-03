package com.example.signin.data.repositories

import android.content.SharedPreferences
import com.example.signin.data.models.SiweResponse
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class SiweRepository @Inject constructor(
    ioDispatcher: CoroutineDispatcher, private val sharedPreferences: SharedPreferences,
    @Named("correctSIWE") private val correctSiweAuthenticateParams: Modal.Params.Authenticate,
    @Named("wrongSIWE") private val wrongSiweAuthenticateParams: Modal.Params.Authenticate
) {
    private val scope = CoroutineScope(ioDispatcher)

    companion object {
        private const val AUTH_UNKNOWN_ERROR_MESSAGE = "SIWE Auth: Unknown error"
        private const val IS_SIWE_AUTHENTICATED = "IS_SIWE_AUTHENTICATED"
    }

    private val _result = MutableSharedFlow<SiweResponse>()
    val result: SharedFlow<SiweResponse> = _result

    fun isSiweAuthenticated() = sharedPreferences.getBoolean(IS_SIWE_AUTHENTICATED, false)
    fun clearSiweAuthentication() =
        sharedPreferences.edit().putBoolean(IS_SIWE_AUTHENTICATED, false).apply()

    fun authenticate(isWrongMessage: Boolean) {
        val authenticateParams = if (isWrongMessage) wrongSiweAuthenticateParams else correctSiweAuthenticateParams
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
                sharedPreferences.edit().putBoolean(IS_SIWE_AUTHENTICATED, true).apply()
                println(response)
                scope.launch {
                    _result.emit(SiweResponse.Success(message = response))
                }
            })
    }
}