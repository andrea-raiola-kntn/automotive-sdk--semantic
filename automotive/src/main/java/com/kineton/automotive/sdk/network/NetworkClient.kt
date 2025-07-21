package com.kineton.automotive.sdk.network

import android.net.TrafficStats
import android.os.Process
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.Base64


object NetworkClient {

    private var baseUrl: String? = null
    lateinit var client: OkHttpClient
        private set

    fun init(baseUrl: String, username: String, password: String) {
        this.baseUrl = baseUrl

        val credentials = basicAuthHeader(username, password)

        client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", credentials)
                    .header("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .addNetworkInterceptor { chain ->
                val uid = Process.myUid()
                val rxBytes = TrafficStats.getUidRxBytes(uid)
                val txBytes = TrafficStats.getUidTxBytes(uid)
                Log.d("NetworkClient", "rxBytes: $rxBytes, txBytes: $txBytes")
                chain.proceed(chain.request())
            }
            .build()
    }

    fun getCall(endpoint: String): Response {
        val url = baseUrl + endpoint

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        return client.newCall(request).execute()
    }

    private fun basicAuthHeader(username: String, password: String): String {
        val auth = "$username:$password"
        val encoded = Base64.getEncoder().encodeToString(auth.toByteArray())
        return "Basic $encoded"
    }

}
