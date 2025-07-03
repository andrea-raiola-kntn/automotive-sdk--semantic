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
    @Test
    fun hello_isCorrect() {
        val sdkObj = AutomotiveSDK()
        assertEquals("Hello World!", sdkObj.hello())
    }

    @Test
    fun hello_haveExclamation() {
        val sdkObj = AutomotiveSDK()
        assertNotEquals("Hello World!", sdkObj.hello())
    }
}