package com.kineton.automotive.sdk

import com.kineton.automotive.sdk.daos.RoomUserDao
import com.kineton.automotive.sdk.managers.RoomManager
import com.kineton.automotive.sdk.repository.RoomUserRepository
import com.kineton.automotive.sdk.repository.UserRepository
import dagger.Module
import dagger.Provides

// Dagger
@Module
internal class AutomotiveSDKModule {
    @Provides
    fun provideRoomUserDao(): RoomUserDao {
        return RoomManager.roomDb.getUserDao()
    }

    @Provides
    fun provideUserRepository(dao: RoomUserDao): UserRepository {
        return RoomUserRepository(dao)
    }

}
