package com.kineton.automotive.sdk

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val sdkObj: AutomotiveSDK by lazy {
        AutomotiveSDK()
    }

    @Test
    fun hello_isCorrect() {
        assertEquals("Hello World!", sdkObj.hello())
    }

    @Test
    fun hello_haveExclamation() {
        assertNotEquals("Hello World", sdkObj.hello())
    }

    @Test
    fun helloWithName_defaultIsCorrect() {
        assertEquals("Hello Paul, Welcome to the AutomotiveSDK", sdkObj.helloWithName())
    }

    @Test
    fun forzaNapoli_isCorrect() {
        assertEquals("Forza Napoli", sdkObj.forzaNapoli())
    }

    @Test
    fun fixBackEnd_isCorrect() {
        assertEquals("Fix Back End", sdkObj.fixBackEnd())
    }
}
