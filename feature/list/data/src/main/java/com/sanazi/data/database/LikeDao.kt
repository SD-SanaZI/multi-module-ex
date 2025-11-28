package com.sanazi.list.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LikeDao {
    @Query("SELECT * FROM likestate")
    fun getAll(): List<LikeState>

    @Upsert
    fun insertAll(vararg states: LikeState)
}