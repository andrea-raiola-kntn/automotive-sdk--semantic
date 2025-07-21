package com.kineton.automotive.sdk.managers

import android.content.Context
import androidx.room.Room
import com.kineton.automotive.sdk.AutomotiveSDKDatabase

object RoomManager {
    lateinit var roomDb: AutomotiveSDKDatabase
        private set

    @JvmOverloads
    fun init(context: Context, isInMemory: Boolean = false) {
        val builder = if (isInMemory) {
            Room.inMemoryDatabaseBuilder(context, AutomotiveSDKDatabase::class.java)
        } else {
            Room.databaseBuilder(context, AutomotiveSDKDatabase::class.java, "automotive-sdk")
        }

        roomDb = builder.build()

    }
}
