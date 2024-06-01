package com.example.etherfi.di

import android.content.Context
import com.example.etherfi.EtherfiApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providesEtherfiApplicationInstance(@ApplicationContext context: Context): EtherfiApplication {
        return context as EtherfiApplication
    }
}