package com.kineton.automotive.sdk.managers

import android.util.Log
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kineton.automotive.sdk.network.interceptors.BasicAuthIntercept
import com.kineton.automotive.sdk.network.interceptors.RequestSizeInterceptor
import com.kineton.automotive.sdk.network.interceptors.ResponseSizeInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkManager {

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
        cacheSizeInMB: Long =  25L * 1024L * 1024L,
        connectionTimeout: Long = 30,
        readTimeout: Long = 30
    ) {
        val cache = Cache(cacheDirectory, cacheSizeInMB)

        val kotlinModule = KotlinModule
            .Builder()
            .build()

        val jacksonMapper = ObjectMapper()
            .registerModule(kotlinModule)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(BasicAuthIntercept(username, password))
            .addInterceptor(RequestSizeInterceptor())
            .addInterceptor(ResponseSizeInterceptor())
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build()

        retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create(jacksonMapper))
            .client(okHttpClient)
            .build()

        httpCache = cache
    }

    internal fun listCache() {
        val urlIterator = httpCache.urls()
        while (urlIterator.hasNext()) {
            Log.d("NetworkManager", "Cached URL: ${urlIterator.next()}")
        }
    }

    private fun evictCache() {
        httpCache.evictAll()
    }

}