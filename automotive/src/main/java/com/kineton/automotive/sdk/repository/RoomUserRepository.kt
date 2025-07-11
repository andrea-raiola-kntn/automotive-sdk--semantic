package com.kineton.automotive.sdk.repository

import com.kineton.automotive.sdk.daos.RoomUserDao
import com.kineton.automotive.sdk.entities.User

class RoomUserRepository(private val dao: RoomUserDao): UserRepository {
    override fun getUserById(id: String): User? = dao.loadAllByIds(listOf(id)).firstOrNull()
    override fun getUserByName(first: String, last: String): User? = dao.findByName(first, last)
    override fun addUser(user: User) = dao.insertAll(user)
    override fun deleteUser(user: User) = dao.delete(user)
}
