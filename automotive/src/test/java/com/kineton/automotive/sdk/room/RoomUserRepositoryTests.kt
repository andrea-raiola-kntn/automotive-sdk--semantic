package com.kineton.automotive.sdk.room

import com.kineton.automotive.sdk.AutomotiveSDKComponent
import com.kineton.automotive.sdk.DaggerAutomotiveSDKComponent
import com.kineton.automotive.sdk.entities.User
import com.kineton.automotive.sdk.managers.RoomManager
import com.kineton.automotive.sdk.modules.DatabaseModule
import com.kineton.automotive.sdk.modules.NetworkModule
import com.kineton.automotive.sdk.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
// TODO we need to replace the Base Manifest
@Config(minSdk = 28)
class RoomUserRepositoryTests {

    lateinit var automotiveSDKComponent: AutomotiveSDKComponent
        private set
    private lateinit var repository: UserRepository

    @Before
    fun `setup database`() {
        RoomManager.init(context = RuntimeEnvironment.getApplication(), isInMemory = true)
        automotiveSDKComponent = DaggerAutomotiveSDKComponent.builder()
            .databaseModule(DatabaseModule())
            .networkModule(Mockito.mock(NetworkModule::class.java))
            .build()


        repository = automotiveSDKComponent.getUserRepository()
    }

    @After
    fun tearDown() {
        RoomManager.roomDb.close()
    }

    @Test
    fun testGetUserById_returnsUser() = runBlocking(Dispatchers.IO) {
        val user = User(uid = "123", firstName = "John", lastName = "Doe")
        repository.addUser(user)

        val result = repository.getUserById("123")

        Assert.assertNotNull(result)
        Assert.assertEquals("John", result?.firstName)
        Assert.assertEquals("Doe", result?.lastName)
    }

    @Test
    fun testGetUserById_returnsNullIfNotFound() = runBlocking(Dispatchers.IO) {
        val result = repository.getUserById("456")
        Assert.assertNull(result)
    }

    @Test
    fun testGetUserByName_returnsUser() = runBlocking(Dispatchers.IO) {
        val user = User(uid = "789", firstName = "Jane", lastName = "Smith")
        repository.addUser(user)

        val result = repository.getUserByName("Jane", "Smith")

        Assert.assertNotNull(result)
        Assert.assertEquals("789", result?.uid)
    }

    @Test
    fun testAddUser_addsCorrectly() = runBlocking(Dispatchers.IO) {
        val user = User(uid = "101", firstName = "Alice", lastName = "Wonderland")
        repository.addUser(user)

        val retrieved = repository.getUserById("101")
        Assert.assertEquals("Alice", retrieved?.firstName)
    }

    @Test
    fun testDeleteUser_deletesCorrectly() = runBlocking(Dispatchers.IO) {
        val user = User(uid = "102", firstName = "Bob", lastName = "Builder")
        repository.addUser(user)

        repository.deleteUser(user)

        val result = repository.getUserById("102")
        Assert.assertNull(result)
    }
}
