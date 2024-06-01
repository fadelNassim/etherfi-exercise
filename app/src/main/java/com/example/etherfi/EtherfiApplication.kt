package com.example.etherfi

import android.app.Application
import android.widget.Toast
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.example.etherfi.di.ConnectWalletEventsDelegate
import com.walletconnect.web3.modal.client.Modal
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.presets.Web3ModalChainsPresets
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EtherfiApplication: Application() {
    @Inject
    lateinit var web3ModalModalDelegate: ConnectWalletEventsDelegate
    override fun onCreate() {
        super.onCreate()
        val connectionType =  ConnectionType.AUTOMATIC
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
        val recommendedWalletsIds = listOf(
            "1ae92b26df02f0abca6304df07debccd18262fdf5fe82daa81593582dac9a369",
            "4622a2b2d6af1c9844944291e5e7351a6aa24cd7b23099efac1b2fd875da31a0"
        )

        CoreClient.initialize(relayServerUrl = serverUrl, connectionType = connectionType, application = this, metaData = appMetaData, onError = {
            println("CoreClient Error: $it")
        })

        val initParams = Modal.Params.Init(core = CoreClient, recommendedWalletsIds = recommendedWalletsIds)
        Web3Modal.initialize(
            init = initParams,
            onSuccess = {
                Toast.makeText(applicationContext,"Web3Modal Success", Toast.LENGTH_SHORT).show()
            },
            onError = { error ->
                println("Web3Model Error: $error")
            }
        )

        Web3Modal.setChains(Web3ModalChainsPresets.ethChains.values.toList())
        Web3Modal.setDelegate(web3ModalModalDelegate)
        Web3Modal.setSessionProperties(
            properties = emptyMap()
        )
    }
}