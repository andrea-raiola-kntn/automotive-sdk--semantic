package com.kineton.automotive.sdk.managers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kineton.automotive.sdk.BuildConfig
import com.kineton.automotive.sdk.network.interceptors.BasicAuthIntercept
import com.kineton.automotive.sdk.network.interceptors.LoggingInterceptor
import com.kineton.automotive.sdk.network.interceptors.RequestSizeInterceptor
import com.kineton.automotive.sdk.network.interceptors.ResponseSizeInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkManager {
    private const val DEFAULT_CACHE_SIZE_MB = 25L
    private const val MB_IN_BYTES = 1024L * 1024L

    private lateinit var httpCache: Cache

    lateinit var retrofitClient: Retrofit
        private set

    @JvmStatic
    @JvmOverloads
    fun init(
        baseUrl: String,
        username: String,
        password: String,
        cacheDirectory: File,
        cacheSizeInBytes: Long? = null,
        connectionTimeout: Long = 30,
        readTimeout: Long = 30
    ) {
        val actualCacheSize = cacheSizeInBytes ?: (DEFAULT_CACHE_SIZE_MB * MB_IN_BYTES)
        val cache = Cache(cacheDirectory, actualCacheSize)

        val kotlinModule = KotlinModule
            .Builder()
            .build()

        val jacksonMapper = ObjectMapper()
            .registerModule(kotlinModule)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(BasicAuthIntercept(username, password))
            .addInterceptor(RequestSizeInterceptor())
            .addInterceptor(ResponseSizeInterceptor())
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        if (BuildConfig.NETWORK_LOGGING) {
            okHttpClientBuilder.addNetworkInterceptor(LoggingInterceptor())
        }

        val okHttpClient = okHttpClientBuilder.build()

        retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create(jacksonMapper))
            .client(okHttpClient)
            .build()

        httpCache = cache
    }

    @JvmSynthetic
    internal fun cacheSize(): Long {
        return httpCache.size()
    }

    @JvmSynthetic
    internal fun evictCache() {
        httpCache.evictAll()
    }

}
