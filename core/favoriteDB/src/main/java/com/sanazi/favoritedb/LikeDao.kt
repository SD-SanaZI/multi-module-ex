package com.sanazi.favoritedb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Query("SELECT * FROM likestate")
    fun getAll(): Flow<List<LikeState>>

    @Upsert
    fun insertAll(vararg states: LikeState)
}