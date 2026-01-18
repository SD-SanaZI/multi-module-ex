package com.sanazi.favoritedb

import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val db: LikeRoomDatabase) :
    UserLocalDataSource {
    override suspend fun getLikes(): List<LikeState> = db.likeDao().getAll()
    override suspend fun insertAll(vararg likeStates: LikeState) = db.likeDao().insertAll(*likeStates)
}