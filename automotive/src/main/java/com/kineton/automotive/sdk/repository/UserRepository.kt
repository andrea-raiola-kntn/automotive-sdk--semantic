package com.kineton.automotive.sdk.repository

import com.kineton.automotive.sdk.entities.User

interface UserRepository {
    suspend fun getUserById(id: String): User?
    suspend fun getUserByName(first: String, last: String): User?
    suspend fun addUser(user: User)
    suspend fun deleteUser(user: User)
}
