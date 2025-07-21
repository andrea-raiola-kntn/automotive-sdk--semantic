package com.kineton.automotive.sdk.modules

import com.kineton.automotive.sdk.network.NetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule(private val networkClient: NetworkClient) {

    @Provides
    @Singleton
    fun provideNetworkClient(): NetworkClient = networkClient

}
