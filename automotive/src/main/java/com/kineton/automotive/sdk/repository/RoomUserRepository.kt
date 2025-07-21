package com.kineton.automotive.sdk.repository

import com.kineton.automotive.sdk.daos.RoomUserDao
import com.kineton.automotive.sdk.entities.User

class RoomUserRepository(private val dao: RoomUserDao): UserRepository {
    override suspend fun getUserById(id: String): User? = dao.loadAllByIds(listOf(id)).firstOrNull()
    override suspend fun getUserByName(first: String, last: String): User? = dao.findByName(first, last)
    override suspend fun addUser(user: User) = dao.insertAll(user)
    override suspend fun deleteUser(user: User) = dao.delete(user)
}
