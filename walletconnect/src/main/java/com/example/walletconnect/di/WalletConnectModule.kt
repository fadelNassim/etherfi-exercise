package com.example.walletconnect.di

import com.example.walletconnect.data.ConnectWalletModalDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WalletConnectModule {
    @Provides
    @Singleton
    fun provideConnectWalledResponseDelegate(ioDispatcher: CoroutineDispatcher): ConnectWalletModalDelegate {
        return ConnectWalletModalDelegate(dispatcher = ioDispatcher)
    }
}