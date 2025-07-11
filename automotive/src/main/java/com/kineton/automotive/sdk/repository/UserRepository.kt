package com.kineton.automotive.sdk.repository

import com.kineton.automotive.sdk.entities.User

interface UserRepository {
    fun getUserById(id: String): User?
    fun getUserByName(first: String, last: String): User?
    fun addUser(user: User)
    fun deleteUser(user: User)
}
