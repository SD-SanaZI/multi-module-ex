package com.sanazi.list.data.database

interface UserLocalDataSource{
    suspend fun getLikes(): List<LikeState>
    suspend fun insertAll(vararg likeStates: LikeState)
}