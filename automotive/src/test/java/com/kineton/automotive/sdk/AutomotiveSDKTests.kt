package com.kineton.automotive.sdk

import androidx.startup.AppInitializer
import com.kineton.automotive.sdk.initializers.CacheInitializer
import com.kineton.automotive.sdk.initializers.MMKVInitializer
import com.kineton.automotive.sdk.initializers.RoomInitializer
import com.kineton.automotive.sdk.managers.CacheManager
import com.kineton.automotive.sdk.managers.RoomManager
import com.kineton.automotive.sdk.modules.DatabaseModule
import com.kineton.automotive.sdk.modules.NetworkModule
import com.kineton.automotive.sdk.managers.NetworkManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
// TODO we need to replace the Base Manifest
@Config(minSdk = 28)
class AutomotiveSDKTests {


    @Before
    fun appStartup() {
        val context = RuntimeEnvironment.getApplication()

        AppInitializer.getInstance(context)
            .initializeComponent(RoomInitializer::class.java)

        AppInitializer.getInstance(context)
            .initializeComponent(CacheInitializer::class.java)

        AppInitializer.getInstance(context)
            .initializeComponent(MMKVInitializer::class.java)
    }


    @Test
    fun `init should work and create the modules for dagger`() = runBlocking {
        NetworkManager.init(
            baseUrl = "https://core-search.radioplayer.cloud",
            username = "Mock_Username",
            password = "Mock_Password",
            cacheDirectory = CacheManager().httpCacheFile
        )
        RoomManager.init(context = RuntimeEnvironment.getApplication(), isInMemory = true)

        val automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .networkModule(NetworkModule(retrofitClient = NetworkManager.retrofitClient))
            .databaseModule(DatabaseModule())
            .build()

        Assert.assertNotNull(automotiveSDKComponent)
        Assert.assertNotNull(automotiveSDKComponent.getUserRepository())
        Assert.assertNotNull(automotiveSDKComponent.getStationService())
    }
}
