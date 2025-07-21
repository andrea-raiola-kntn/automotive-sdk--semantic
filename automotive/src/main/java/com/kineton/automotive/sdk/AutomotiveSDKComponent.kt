package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.modules.DatabaseModule
import com.kineton.automotive.sdk.modules.DispatcherModule
import com.kineton.automotive.sdk.modules.NetworkModule
import com.kineton.automotive.sdk.modules.StationServiceModule
import com.kineton.automotive.sdk.repository.UserRepository
import dagger.Component
import javax.inject.Singleton

// Dagger Application graph
@Singleton
@Component(modules = [
    DatabaseModule::class,
    NetworkModule::class,
    DispatcherModule::class,
    StationServiceModule::class
])
interface AutomotiveSDKComponent {
    fun getUserRepository(): UserRepository
    fun getStationService(): StationService
}
