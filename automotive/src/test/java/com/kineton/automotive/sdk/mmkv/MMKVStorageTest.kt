package com.kineton.automotive.sdk.mmkv

import com.kineton.automotive.sdk.storage.MMKVStorage
import com.tencent.mmkv.MMKV
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class MMKVStorageTest {

    private lateinit var mmkv: MMKV
    private lateinit var storage: MMKVStorage

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mmkv = mock(MMKV::class.java)
        storage = MMKVStorage(mmkv)
    }

    @Test
    fun `write and read value should call mmkv methods`() {
        `when`(mmkv.encode("test_key", "hello_mmkv")).thenReturn(true)
        `when`(mmkv.decodeString("test_key")).thenReturn("hello_mmkv")

        val writeResult = storage.encode("test_key", "hello_mmkv")
        val readResult = storage.decodeString("test_key")

        assertEquals(true, writeResult)
        assertEquals("hello_mmkv", readResult)

        verify(mmkv).encode("test_key", "hello_mmkv")
        verify(mmkv).decodeString("test_key")
    }

    @Test
    fun `decode unknown key should return null`() {
        `when`(mmkv.decodeString("unknown_key")).thenReturn(null)
        val result = storage.decodeString("unknown_key")
        assertNull(result)
        verify(mmkv).decodeString("unknown_key")
    }

    @Test
    fun `contains key should return false if the key is not found`() {
        `when`(mmkv.containsKey("key")).thenReturn(false)
        val result = storage.containsKey("key")
        Assert.assertFalse(result)
        verify(mmkv).containsKey("key")
    }
}
