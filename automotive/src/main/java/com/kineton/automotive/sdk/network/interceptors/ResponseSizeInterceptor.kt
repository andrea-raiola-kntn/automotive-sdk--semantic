package com.kineton.automotive.sdk.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseSizeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val responseBody = response.body

        val source = responseBody.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer.clone()

        val sizeInBytes = buffer.size

        val logStats = ">>> [${request.method}] ${request.url} -> Response size: $sizeInBytes bytes"
        Log.d("ResponseSizeInterceptor", logStats)

        return response
    }
}
