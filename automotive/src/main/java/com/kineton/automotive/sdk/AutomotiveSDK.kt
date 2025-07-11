package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.entities.User
import com.kineton.automotive.sdk.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class AutomotiveSDK(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val userRepository: UserRepository by lazy {
        DaggerAutomotiveSDKComponent.create().getUserRepository()
    }

    fun hello(): String {
        val hello = "Hello World!"
        return hello
    }

    fun helloWithName(name: String? = "Paul"): String {
        return "Hello $name, Welcome to the AutomotiveSDK"
    }

    fun forzaNapoli(): String {
        val forzaNapoli = "Forza Napoli"
        return forzaNapoli
    }

    fun fixBackEnd(): String {
        val fixBackEnd = "Fix Back End"
        return fixBackEnd
    }

    suspend fun testDagger(): String = withContext(dispatcher) {
        userRepository.addUser(
            User(
                uid = UUID.randomUUID().toString(),
                firstName = "Francesco",
                lastName = "Virgolini"
            )
        )

        val user = userRepository.getUserByName("Francesco", "Virgolini")
        helloWithName("${user?.firstName} ${user?.lastName}")
    }
}
