package com.kineton.automotive.sdk.room

import com.kineton.automotive.sdk.daos.RoomUserDao
import com.kineton.automotive.sdk.entities.User
import com.kineton.automotive.sdk.repository.RoomUserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RoomUserRepositoryTests {

    private lateinit var dao: RoomUserDao
    private lateinit var repository: RoomUserRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dao = Mockito.mock(RoomUserDao::class.java)
        repository = RoomUserRepository(dao)
    }

    @Test
    fun testGetUserById_returnsUser() {
        val user = User(uid = "123", firstName = "John", lastName = "Doe")
        Mockito.`when`(dao.loadAllByIds(listOf("123"))).thenReturn(listOf(user))

        val result = repository.getUserById("123")

        Assert.assertNotNull(result)
        Assert.assertEquals("John", result?.firstName)
        Assert.assertEquals("Doe", result?.lastName)

        Mockito.verify(dao).loadAllByIds(listOf("123"))
    }

    @Test
    fun testGetUserById_returnsNullIfNotFound() {
        Mockito.`when`(dao.loadAllByIds(listOf("456"))).thenReturn(emptyList())

        val result = repository.getUserById("456")

        Assert.assertNull(result)
        Mockito.verify(dao).loadAllByIds(listOf("456"))
    }

    @Test
    fun testGetUserByName_returnsUser() {
        val user = User(uid = "789", firstName = "Jane", lastName = "Smith")
        Mockito.`when`(dao.findByName("Jane", "Smith")).thenReturn(user)

        val result = repository.getUserByName("Jane", "Smith")

        Assert.assertNotNull(result)
        Assert.assertEquals("789", result?.uid)
        Mockito.verify(dao).findByName("Jane", "Smith")
    }

    @Test
    fun testAddUser_callsInsert() {
        val user = User(uid = "101", firstName = "Alice", lastName = "Wonderland")
        Mockito.doNothing().`when`(dao).insertAll(user)

        repository.addUser(user)

        Mockito.verify(dao).insertAll(user)
    }

    @Test
    fun testDeleteUser_callsDelete() {
        val user = User(uid = "102", firstName = "Bob", lastName = "Builder")
        Mockito.doNothing().`when`(dao).delete(user)

        repository.deleteUser(user)

        Mockito.verify(dao).delete(user)
    }
}
