package com.kineton.automotive.sdk

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        // The com.android.library doesn't have a package name, so to build the fake "apk" it will add .test
        val appContext = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.kineton.automotive.sdk.test", appContext.packageName)
    }
}