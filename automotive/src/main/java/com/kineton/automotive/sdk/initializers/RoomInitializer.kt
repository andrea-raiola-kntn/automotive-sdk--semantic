package com.kineton.automotive.sdk.initializers

import android.content.Context
import androidx.startup.Initializer
import com.kineton.automotive.sdk.managers.RoomManager

class RoomInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        RoomManager.init(
            context = context,
            isInMemory = false
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}
