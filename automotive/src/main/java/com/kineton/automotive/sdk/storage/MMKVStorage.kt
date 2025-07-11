package com.kineton.automotive.sdk.storage

import com.tencent.mmkv.MMKV

class MMKVStorage(private val mmkv: MMKV): Storage {
    override fun encode(key: String, value: String): Boolean {
        return mmkv.encode(key, value)
    }

    override fun decodeString(key: String): String? {
        return mmkv.decodeString(key)
    }

    override fun containsKey(key: String): Boolean {
        return mmkv.containsKey(key)
    }
}
