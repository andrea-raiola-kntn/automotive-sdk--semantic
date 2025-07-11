package com.kineton.automotive.sdk.storage

interface Storage {
    fun encode(key: String, value: String): Boolean
    fun decodeString(key: String): String?
    fun containsKey(key: String): Boolean
}
