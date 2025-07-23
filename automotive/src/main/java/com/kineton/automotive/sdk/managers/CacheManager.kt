package com.kineton.automotive.sdk.managers

import android.content.Context

import java.io.File

object CacheManager {
    lateinit var httpCacheFile: File
        private set

    fun init(context: Context) {
        httpCacheFile = File(context.cacheDir, "http_cache")
    }
}
