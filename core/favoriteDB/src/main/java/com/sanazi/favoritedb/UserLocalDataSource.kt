package com.sanazi.favoritedb

interface UserLocalDataSource{
    suspend fun getLikes(): List<LikeState>
    suspend fun insertAll(vararg likeStates: LikeState)
}