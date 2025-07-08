package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.interfaces.Repository
import dagger.Component

// Dagger Application graph
@Component(modules = [AutomotiveSDKModule::class])
fun interface AutomotiveSDKComponent {
    fun getRepository(): Repository
}