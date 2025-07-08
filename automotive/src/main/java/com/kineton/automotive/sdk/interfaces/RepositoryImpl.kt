package com.kineton.automotive.sdk.interfaces

import jakarta.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    override fun getData() = "Hello from Repository"
}