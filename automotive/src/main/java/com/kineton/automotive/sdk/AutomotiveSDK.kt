package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.modules.DatabaseModule
import com.kineton.automotive.sdk.modules.NetworkModule
import com.kineton.automotive.sdk.network.NetworkClient

class AutomotiveSDK {
    lateinit var automotiveSDKComponent: AutomotiveSDKComponent
        private set

    @JvmOverloads
    fun init(username: String, password: String, baseUrl: String = "https://core-search.radioplayer.cloud") {
        print("AutomotiveSDK init...")
        NetworkClient.init(
            baseUrl = baseUrl,
            username = username,
            password = password
        )

        automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule(NetworkClient))
            .build()
    }
}
