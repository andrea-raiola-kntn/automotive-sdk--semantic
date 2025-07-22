package com.kineton.automotive.sdk.initializers

import android.content.Context
import androidx.startup.Initializer
import com.kineton.automotive.sdk.managers.CacheManager

class CacheInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        CacheManager().init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}