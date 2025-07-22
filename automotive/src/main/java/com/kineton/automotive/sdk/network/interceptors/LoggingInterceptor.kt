package com.kineton.automotive.sdk.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {

    companion object {
        private const val TAG = "LoggingInterceptor"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        Log.d(TAG, "Sending request ${request.url} on ${chain.connection()}\n${request.headers}")

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Log.d(TAG, "Received response for ${response.request.url} in ${(t2 - t1) / 1e6}ms\n${response.headers}")

        return response
    }
}
