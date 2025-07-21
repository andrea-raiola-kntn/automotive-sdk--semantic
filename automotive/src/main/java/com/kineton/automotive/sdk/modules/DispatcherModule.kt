package com.kineton.automotive.sdk.modules

import com.kineton.automotive.sdk.qualifier.IoDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatcherModule {
    @Provides
    @IoDispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

//    @Provides
//    @MainDispatcher
//    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
