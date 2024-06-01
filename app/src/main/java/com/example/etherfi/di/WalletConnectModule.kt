package com.example.etherfi.di

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
    fun provideConnectWalledResponseDelegate(dispatcher: CoroutineDispatcher): ConnectWalletEventsDelegate {
        return ConnectWalletEventsDelegate(dispatcher = dispatcher)
    }
}