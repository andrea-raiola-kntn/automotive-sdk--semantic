package com.kineton.automotive.sdk

class AutomotiveSDK {

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
}
