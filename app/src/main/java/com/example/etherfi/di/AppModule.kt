package com.example.etherfi.di

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.example.etherfi.EtherfiApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    companion object {
        const val SHARED_PREFERENCES_NAME = "etherfi"
    }

    @Provides
    @Singleton
    fun providesEtherfiApplicationInstance(@ApplicationContext context: Context): EtherfiApplication {
        return context as EtherfiApplication
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context) = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}