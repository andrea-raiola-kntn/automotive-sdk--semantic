package com.kineton.automotive.sdk.network

import com.kineton.automotive.sdk.AutomotiveSDKComponent
import com.kineton.automotive.sdk.DaggerAutomotiveSDKComponent
import com.kineton.automotive.sdk.modules.NetworkModule
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
// TODO we need to replace the Base Manifest
@Config(minSdk = 28)
class StationServiceTests {

    lateinit var automotiveSDKComponent: AutomotiveSDKComponent
        private set

    @Before
    fun initNetworkClient(){
        NetworkClient.init(
            baseUrl = "https://core-search.radioplayer.cloud",
            username = RadioplayerCredentials.username,
            password = RadioplayerCredentials.password
        )

        automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .networkModule(NetworkModule(networkClient = NetworkClient))
            .build()
    }


    @Test
    fun `retrieve stations should return 200 HTTP OK`() {
        val promise = automotiveSDKComponent.getStationService().retrieveStationsAsync()
        val string = promise.get()
        assert(string.contains("services"))
    }
}
