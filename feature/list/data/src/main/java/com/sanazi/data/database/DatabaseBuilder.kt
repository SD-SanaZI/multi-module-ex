package com.sanazi.list.data.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: LikeRoomDatabase? = null
    fun getInstance(context: Context): LikeRoomDatabase {
        if (INSTANCE == null) {
            synchronized(LikeRoomDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            LikeRoomDatabase::class.java,
            "LikeRoomDatabase"
        ).build()
}