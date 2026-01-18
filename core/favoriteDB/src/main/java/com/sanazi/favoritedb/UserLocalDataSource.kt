package com.sanazi.favoritedb

import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource{
    suspend fun getLikes(): Flow<List<LikeState>>
    suspend fun insertAll(vararg likeStates: LikeState)
}