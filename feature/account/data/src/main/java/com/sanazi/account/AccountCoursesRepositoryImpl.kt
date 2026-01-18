package com.sanazi.account

import com.sanazi.favoritedb.LikeState
import com.sanazi.favoritedb.UserLocalDataSource
import com.sanazi.network.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountCoursesRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : AccountCoursesRepository {
    override suspend fun getAllCourses(): Flow<List<AccountCourse>> {
        return withContext(Dispatchers.IO) {
            val netCourses = remoteDataSource.getCourses().toMutableList()
            localDataSource.getLikes().map { likeList ->
                netCourses.map { course ->
                    course.copy(
                        hasLike = likeList
                            .firstOrNull { it.uid == course.id }
                            ?.hasLike ?: course.hasLike
                    )
                }.map { course ->
                    AccountCourseMapper.mapFromCourse(course)
                }
            }
        }
    }

    override suspend fun setFavorite(id: Int, hasLike: Boolean){
        localDataSource.insertAll(LikeState(id, hasLike))
    }
}