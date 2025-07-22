package com.kineton.automotive.sdk.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestSizeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body

        var sizeInBytes = 0L

        if (requestBody != null) {
            val buffer = okio.Buffer()
            requestBody.writeTo(buffer)
            sizeInBytes = buffer.size
        }

        val logStats = ">>> [${request.method}] ${request.url} -> Request size: $sizeInBytes bytes"
        Log.d("RequestSizeInterceptor", logStats)

        return chain.proceed(request)
    }
}
