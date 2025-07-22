package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.managers.CacheManager
import com.kineton.automotive.sdk.modules.DatabaseModule
import com.kineton.automotive.sdk.modules.NetworkModule
import com.kineton.automotive.sdk.managers.NetworkManager

class AutomotiveSDK {
    lateinit var automotiveSDKComponent: AutomotiveSDKComponent
        private set

    @JvmOverloads
    fun init(username: String, password: String, baseUrl: String = "https://core-search.radioplayer.cloud") {
        print("AutomotiveSDK init...")
        NetworkManager.init(
            baseUrl = baseUrl,
            username = username,
            password = password,
            cacheDirectory = CacheManager.httpCacheFile
        )

        val networkModule = NetworkModule(NetworkManager.retrofitClient)
        automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .databaseModule(DatabaseModule())
            .networkModule(networkModule)
            .build()
    }
}
