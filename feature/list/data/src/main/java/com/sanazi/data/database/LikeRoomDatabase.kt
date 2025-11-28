package com.sanazi.list.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LikeState::class], version = 1)
abstract class LikeRoomDatabase : RoomDatabase() {
    abstract fun likeDao(): LikeDao
}