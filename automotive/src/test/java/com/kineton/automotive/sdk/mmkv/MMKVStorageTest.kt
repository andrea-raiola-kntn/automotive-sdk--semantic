package com.kineton.automotive.sdk.mmkv

import com.kineton.automotive.sdk.managers.MMKVManager
import com.kineton.automotive.sdk.storage.MMKVStorage
import com.tencent.mmkv.MMKV
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
class MMKVStorageTest {

    private lateinit var mmkv: MMKV
    private lateinit var storage: MMKVStorage

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mmkv = Mockito.mock(MMKV::class.java)
        storage = MMKVStorage(mmkv)
    }

    @Test
    fun `write and read value should call mmkv methods`() {
        Mockito.`when`(mmkv.encode("test_key", "hello_mmkv")).thenReturn(true)
        Mockito.`when`(mmkv.decodeString("test_key")).thenReturn("hello_mmkv")

        val writeResult = storage.encode("test_key", "hello_mmkv")
        val readResult = storage.decodeString("test_key")

        Assert.assertEquals(true, writeResult)
        Assert.assertEquals("hello_mmkv", readResult)

        Mockito.verify(mmkv).encode("test_key", "hello_mmkv")
        Mockito.verify(mmkv).decodeString("test_key")
    }

    @Test
    fun `decode unknown key should return null`() {
        Mockito.`when`(mmkv.decodeString("unknown_key")).thenReturn(null)
        val result = storage.decodeString("unknown_key")
        Assert.assertNull(result)
        Mockito.verify(mmkv).decodeString("unknown_key")
    }

    @Test
    fun `contains key should return false if the key is not found`() {
        Mockito.`when`(mmkv.containsKey("key")).thenReturn(false)
        val result = storage.containsKey("key")
        Assert.assertFalse(result)
        Mockito.verify(mmkv).containsKey("key")
    }
}
