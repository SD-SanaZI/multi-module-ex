package com.sanazi.favoritedb

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: LikeRoomDatabase? = null
    fun getInstance(context: Context): LikeRoomDatabase {
        return INSTANCE?: synchronized(LikeRoomDatabase::class) {
            return@synchronized buildRoomDB(context).also {
                INSTANCE = buildRoomDB(context)
            }
        }
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            LikeRoomDatabase::class.java,
            "LikeRoomDatabase"
        ).build()
}