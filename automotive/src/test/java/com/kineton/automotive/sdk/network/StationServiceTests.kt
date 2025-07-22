package com.kineton.automotive.sdk.network

import com.kineton.automotive.sdk.AutomotiveSDKComponent
import com.kineton.automotive.sdk.DaggerAutomotiveSDKComponent
import com.kineton.automotive.sdk.dtos.RadioStation
import com.kineton.automotive.sdk.managers.CacheManager
import com.kineton.automotive.sdk.managers.NetworkManager
import com.kineton.automotive.sdk.modules.NetworkModule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(minSdk = 28, manifest = Config.NONE)
class StationServiceTests {

    lateinit var automotiveSDKComponent: AutomotiveSDKComponent
        private set

    @Before
    fun initNetworkClient(){
        val context = RuntimeEnvironment.getApplication()
        check(context.packageName.contains(".test")) {
            "This test must run in a test environment"
        }

        CacheManager.init(context)

        NetworkManager.init(
            baseUrl = "https://core-search.radioplayer.cloud",
            username = RadioplayerCredentials.username,
            password = RadioplayerCredentials.password,
            cacheDirectory = CacheManager.httpCacheFile
        )

        automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .networkModule(NetworkModule(retrofitClient = NetworkManager.retrofitClient))
            .build()
    }

    @After
    fun tearDown() {
        NetworkManager.evictCache()
    }


    @Test
    fun `retrieve stations should return an Object of type List of RadioStation`(): Unit = runBlocking {
        val stations = automotiveSDKComponent.getStationService().retrieveStationsAsync().get()

        Assert.assertNotNull(stations)
        Assert.assertTrue(stations is List<*>)
    }

    @Test
    fun `retrieve station should return an Object of type RadioStation`() {
        val station = automotiveSDKComponent.getStationService()
            .retrieveRadioStationByRpIdAsync("117")
            .get()

        Assert.assertNotNull(station)
        Assert.assertTrue(station is RadioStation)
    }

    @Test
    fun `cache should be empty`() {
        Assert.assertEquals(0L,NetworkManager.cacheSize())
    }

    @Test
    fun `cache should be not empty`() {
        automotiveSDKComponent.getStationService().retrieveStationsAsync().get()
        Assert.assertTrue(NetworkManager.cacheSize() > 0L)
    }
}
