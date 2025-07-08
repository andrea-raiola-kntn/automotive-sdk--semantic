package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.interfaces.Repository
import com.kineton.automotive.sdk.interfaces.RepositoryImpl
import dagger.Module
import dagger.Provides

// Dagger
@Module
internal class AutomotiveSDKModule {
    @Provides
    fun provideRepository(): Repository = RepositoryImpl()
}