package com.kineton.automotive.sdk

import android.content.Context
import androidx.startup.AppInitializer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kineton.automotive.sdk.initializers.MMKVInitializer
import com.kineton.automotive.sdk.managers.MMKVManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MMKVTests {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        AppInitializer.getInstance(context)
            .initializeComponent(MMKVInitializer::class.java)
    }

    @Test
    fun writeAndReadValueInMMKV_shouldReturnStoredValue() {
        val testKey = "test_key"
        val testValue = "hello_mmkv"

        MMKVManager.storage.encode(testKey, testValue)
        val readValue = MMKVManager.storage.decodeString(testKey)

        assertEquals("MMKV should correctly store and retrieve values", testValue, readValue)
    }
}
