package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.repository.UserRepository
import dagger.Component

// Dagger Application graph
@Component(modules = [AutomotiveSDKModule::class])
fun interface AutomotiveSDKComponent {
    fun getUserRepository(): UserRepository
}
