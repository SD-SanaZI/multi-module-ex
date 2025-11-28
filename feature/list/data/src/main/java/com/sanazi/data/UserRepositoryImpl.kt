package com.sanazi.list.data

import com.sanazi.list.data.database.LikeState
import com.sanazi.list.data.database.UserLocalDataSource
import com.sanazi.list.data.net.UserRemoteDataSource
import com.sanazi.list.domain.UserRepository
import com.sanazi.list.domain.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun update(): List<Course> {
        return withContext(Dispatchers.IO) {
            val netCourses = remoteDataSource.getCourses()
            val newCourses = netCourses.map { course ->
                course.copy(
                    hasLike = localDataSource.getLikes()
                        .firstOrNull { it.uid == course.id }
                        ?.hasLike ?: course.hasLike
                )
            }
            return@withContext newCourses
        }
    }

    override suspend fun setFavorite(id: Int, hasLike: Boolean){
        localDataSource.insertAll(LikeState(id, hasLike))
    }
}