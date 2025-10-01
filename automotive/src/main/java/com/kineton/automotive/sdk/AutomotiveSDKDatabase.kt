package com.kineton.automotive.sdk

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kineton.automotive.sdk.daos.RoomUserDao
import com.kineton.automotive.sdk.entities.User


@Database(entities = [User::class], version = 1)
abstract class AutomotiveSDKDatabase : RoomDatabase() {
    abstract fun getUserDao(): RoomUserDao
}

