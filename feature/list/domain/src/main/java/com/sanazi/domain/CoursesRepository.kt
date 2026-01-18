package com.sanazi.list.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CoursesRepository{
    suspend fun getAllCourses(): Flow<List<ListCourse>>
    suspend fun setFavorite(id: Int, hasLike: Boolean)
}

class GetAllCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): Flow<List<ListCourse>> {
        return repository.getAllCourses()
    }
}

class GetSortedCoursesUseCase @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase
) {
    suspend operator fun invoke(): Flow<List<ListCourse>> {
        return getAllCoursesUseCase()
            .map { list -> list.sortedBy { it.publishDate } }
    }
}

class GetReverseSortedCoursesUseCase @Inject constructor(
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase
) {
    suspend operator fun invoke(): Flow<List<ListCourse>>  {
        return getSortedCoursesUseCase()
            .map { it.reversed() }
    }
}

class SetFavoriteUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(id: Int, hasLike: Boolean) {
        return repository.setFavorite(id, hasLike)
    }
}