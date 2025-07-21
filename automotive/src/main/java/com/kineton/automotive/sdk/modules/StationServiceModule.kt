package com.kineton.automotive.sdk.modules

import com.kineton.automotive.sdk.StationServiceImpl
import com.kineton.automotive.sdk.StationService
import dagger.Binds
import dagger.Module

@Module
abstract class StationServiceModule {

    @Binds
    abstract fun bindStationService(
        impl: StationServiceImpl
    ): StationService
}
