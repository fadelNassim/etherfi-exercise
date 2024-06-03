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
    companion object {
        const val SHARED_PREFERENCES_NAME = "etherfi"
    }

    @Provides
    fun providesEtherfiApplicationInstance(@ApplicationContext context: Context): EtherfiApplication {
        return context as EtherfiApplication
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
}