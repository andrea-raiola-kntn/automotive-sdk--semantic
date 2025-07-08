package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.interfaces.Repository

class AutomotiveSDK {

    private val repository: Repository by lazy {
        DaggerAutomotiveSDKComponent.create().getRepository()
    }

    fun hello(): String {
        val hello = "Hello World!"
        return hello
    }

    fun helloWithName(name: String = "Paul"): String {
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

    fun testDagger(): String {
            return this.repository.getData()
    }
}
