package com.kineton.automotive.sdk.managers

import android.content.Context
import com.kineton.automotive.sdk.storage.MMKVStorage
import com.kineton.automotive.sdk.storage.Storage
import com.tencent.mmkv.MMKV

object MMKVManager {

    lateinit var storage: Storage
        private set

    fun init(context: Context) {
        MMKV.initialize(context)
        val mmkv = MMKV.defaultMMKV()
        storage = MMKVStorage(mmkv)
    }
}
