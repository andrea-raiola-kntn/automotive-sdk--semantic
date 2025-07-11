package com.kineton.automotive.sdk

import android.content.Context
import androidx.startup.AppInitializer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kineton.automotive.sdk.entities.User
import com.kineton.automotive.sdk.initializers.RoomInitializer
import com.kineton.automotive.sdk.managers.RoomManager
import com.kineton.automotive.sdk.repository.RoomUserRepository
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class RoomUserRepositoryInstrumentedTests {

    private lateinit var repository: RoomUserRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        AppInitializer.getInstance(context)
            .initializeComponent(RoomInitializer::class.java)
        repository = RoomUserRepository(RoomManager.roomDb.getUserDao())
    }

    @After
    fun tearDown() {
        RoomManager.roomDb.close()
    }

    @Test
    fun testGetUserById_returnsUser() {
        val user = User(uid = "123", firstName = "John", lastName = "Doe")
        repository.addUser(user)

        val result = repository.getUserById("123")

        Assert.assertNotNull(result)
        Assert.assertEquals("John", result?.firstName)
        Assert.assertEquals("Doe", result?.lastName)
    }

    @Test
    fun testGetUserById_returnsNullIfNotFound() {
        val result = repository.getUserById("456")
        Assert.assertNull(result)
    }

    @Test
    fun testGetUserByName_returnsUser() {
        val user = User(uid = "789", firstName = "Jane", lastName = "Smith")
        repository.addUser(user)

        val result = repository.getUserByName("Jane", "Smith")

        Assert.assertNotNull(result)
        Assert.assertEquals("789", result?.uid)
    }

    @Test
    fun testAddUser_addsCorrectly() {
        val user = User(uid = "101", firstName = "Alice", lastName = "Wonderland")
        repository.addUser(user)

        val retrieved = repository.getUserById("101")
        Assert.assertEquals("Alice", retrieved?.firstName)
    }

    @Test
    fun testDeleteUser_deletesCorrectly() {
        val user = User(uid = "102", firstName = "Bob", lastName = "Builder")
        repository.addUser(user)

        repository.deleteUser(user)

        val result = repository.getUserById("102")
        Assert.assertNull(result)
    }
}
