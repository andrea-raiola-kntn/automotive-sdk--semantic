package com.kineton.automotive.sdk.modules

import com.kineton.automotive.sdk.network.apis.RadioStationApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule(private val retrofitClient: Retrofit) {
    @Provides
    @Singleton
    fun provideNetworkClient(): Retrofit = retrofitClient

    @Provides
    fun provideRadioStationApiService(retrofit: Retrofit): RadioStationApiService =
        retrofit.create(RadioStationApiService::class.java)

}
