package com.example.etherfi

import android.app.Application
import com.example.walletconnect.data.ConnectWalletModalDelegate
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EtherfiApplication : Application() {
    @Inject
    lateinit var web3ModalModalDelegate: ConnectWalletModalDelegate

    private fun setupModal() {
        val initParams = Modal.Params.Init(core = CoreClient)
        Web3Modal.initialize(
            init = initParams,
            onSuccess = {
                println("Web3Modal Success")
            },
            onError = { error ->
                println("Web3Modal Error: $error")
            }
        )
        Web3Modal.setChains(Web3ModalChainsPresets.ethChains.values.toList())
        Web3Modal.setDelegate(web3ModalModalDelegate)
        Web3Modal.setSessionProperties(
            properties = emptyMap()
        )
    }

    /** Setup CoreClient, hardcoded values for exercise purpose,
     *  must be in local.properties and loaded according to the buildType */
    fun setupCoreClient() {
        val connectionType = ConnectionType.AUTOMATIC
        val projectId = "6df9221c03aa9df8f02c5fdf1a50d235"
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=${projectId}"
        val appMetaData = Core.Model.AppMetaData(
            name = "Kotlin.Web3Modal",
            description = "Kotlin Web3Modal Implementation",
            url = "kotlin.walletconnect.com",
            icons = listOf("https://raw.githubusercontent.com/WalletConnect/walletconnect-assets/master/Icon/Gradient/Icon.png"),
            redirect = "kotlin-web3modal://request"
        )

        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = connectionType,
            application = this,
            metaData = appMetaData,
            onError = {
                println("CoreClient Error: $it")
            })
    }

    override fun onCreate() {
        super.onCreate()
        setupCoreClient()
        setupModal()
    }
}