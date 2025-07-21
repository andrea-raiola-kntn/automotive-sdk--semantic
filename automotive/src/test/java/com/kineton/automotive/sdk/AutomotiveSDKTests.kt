package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.managers.RoomManager
import com.kineton.automotive.sdk.modules.DatabaseModule
import com.kineton.automotive.sdk.modules.NetworkModule
import com.kineton.automotive.sdk.network.NetworkClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
// TODO we need to replace the Base Manifest
@Config(minSdk = 28)
class AutomotiveSDKTests {


    @Test
    fun `init shoudl work and create the modules for dagger`() = runBlocking {
        NetworkClient.init(
            baseUrl = "Mock_Base_Url",
            username = "Mock_Username",
            password = "Mock_Password"
        )
        RoomManager.init(context = RuntimeEnvironment.getApplication(), isInMemory = true)

        val automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .networkModule(NetworkModule(networkClient = NetworkClient))
            .databaseModule(DatabaseModule())
            .build()

        Assert.assertNotNull(automotiveSDKComponent)
        Assert.assertNotNull(automotiveSDKComponent.getUserRepository())
        Assert.assertNotNull(automotiveSDKComponent.getStationService())
    }
}
