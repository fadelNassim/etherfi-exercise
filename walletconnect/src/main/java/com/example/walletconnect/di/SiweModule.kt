package com.example.walletconnect.di

import com.walletconnect.util.bytesToHex
import com.walletconnect.util.randomBytes
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/** Hardcoded values for exercise purpose,
 *  must be in local.properties and loaded according to the buildType */
@Module
@InstallIn(SingletonComponent::class)
class SiweModule {
    @Provides
    @Singleton
    @Named("correctSIWE")
    fun provideSiweAuthenticateParams() = Modal.Params.Authenticate(
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

    @Provides
    @Singleton
    @Named("wrongSIWE")
    fun provideWrongSiweAuthenticateParams() = Modal.Params.Authenticate(
        chains = emptyList(),
        domain = "",
        uri = "",
        nonce = "",
        exp = null,
        nbf = null,
        statement = "Sign in with wallet.",
        requestId = null,
        resources = listOf(),
        methods = null,
        expiry = null
    )
}