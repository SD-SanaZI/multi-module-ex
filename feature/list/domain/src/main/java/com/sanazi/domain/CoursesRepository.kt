package com.sanazi.list.domain

import javax.inject.Inject

interface CoursesRepository{
    suspend fun getAllCourses(): List<ListCourse>
    suspend fun setFavorite(id: Int, hasLike: Boolean)
}

class GetAllCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): List<ListCourse> {
        return repository.getAllCourses()
    }
}

class GetSortedCoursesUseCase @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase
) {
    suspend operator fun invoke(): List<ListCourse> {
        return getAllCoursesUseCase().sortedBy { it.publishDate }
    }
}

class GetReverseSortedCoursesUseCase @Inject constructor(
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase
) {
    suspend operator fun invoke(): List<ListCourse> {
        return getSortedCoursesUseCase().reversed()
    }
}

class SetFavoriteUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(id: Int, hasLike: Boolean) {
        return repository.setFavorite(id, hasLike)
    }
}