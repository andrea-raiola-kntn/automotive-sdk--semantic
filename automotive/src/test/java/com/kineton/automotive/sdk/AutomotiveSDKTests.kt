package com.kineton.automotive.sdk

import androidx.startup.AppInitializer
import com.kineton.automotive.sdk.initializers.CacheInitializer
import com.kineton.automotive.sdk.initializers.RoomInitializer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(minSdk = 28, manifest = Config.NONE)
class AutomotiveSDKTests {

    @Before
    fun `manual run of initializers`() {
        val context = RuntimeEnvironment.getApplication()
        check(context.packageName.contains(".test")) {
            "This test must run in a test environment"
        }

        AppInitializer.getInstance(context)
            .initializeComponent(RoomInitializer::class.java)

        AppInitializer.getInstance(context)
            .initializeComponent(CacheInitializer::class.java)

// TODO need to mock this
//        AppInitializer.getInstance(context)
//            .initializeComponent(MMKVInitializer::class.java)
    }


    @Test
    fun `init should work and create the modules for dagger`() {
        val sdkObj = AutomotiveSDK()
        sdkObj.init(
            baseUrl = "https://core-search.radioplayer.cloud",
            username = "Mock_Username",
            password = "Mock_Password",
        )

        Assert.assertNotNull(sdkObj.automotiveSDKComponent)
        Assert.assertNotNull(sdkObj.automotiveSDKComponent.getUserRepository())
        Assert.assertNotNull(sdkObj.automotiveSDKComponent.getStationService())
    }
}
