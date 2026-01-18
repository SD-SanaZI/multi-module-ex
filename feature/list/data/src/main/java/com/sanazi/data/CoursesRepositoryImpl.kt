package com.sanazi.list.data

import com.sanazi.data.ListCourseMapper
import com.sanazi.favoritedb.LikeState
import com.sanazi.favoritedb.UserLocalDataSource
import com.sanazi.list.domain.CoursesRepository
import com.sanazi.list.domain.ListCourse
import com.sanazi.network.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : CoursesRepository {
    override suspend fun getAllCourses(): Flow<List<ListCourse>> {
        return withContext(Dispatchers.IO) {
            val netCourses = remoteDataSource.getCourses()
            localDataSource.getLikes().map { likeList ->
                netCourses.map { course ->
                    course.copy(
                        hasLike = likeList
                            .firstOrNull { it.uid == course.id }
                            ?.hasLike ?: course.hasLike
                    )
                }.map { course ->
                    ListCourseMapper.mapFromCourse(course)
                }
            }
        }
    }

    override suspend fun setFavorite(id: Int, hasLike: Boolean){
        localDataSource.insertAll(LikeState(id, hasLike))
    }
}