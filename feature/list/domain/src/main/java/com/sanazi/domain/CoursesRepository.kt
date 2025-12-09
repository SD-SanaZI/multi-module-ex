package com.sanazi.list.domain

import javax.inject.Inject

interface CoursesRepository{
    suspend fun getAllCourses(): List<Course>
    suspend fun setFavorite(id: Int, hasLike: Boolean)
}

class GetAllCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): List<Course> {
        return repository.getAllCourses()
    }
}

class GetSortedCoursesUseCase @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase
) {
    suspend operator fun invoke(): List<Course> {
        return getAllCoursesUseCase().sortedBy { it.publishDate }
    }
}

class GetReverseSortedCoursesUseCase @Inject constructor(
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase
) {
    suspend operator fun invoke(): List<Course> {
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

class CoursesManager @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase,
    private val getReverseSortedCoursesUseCase: GetReverseSortedCoursesUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
){
    suspend fun getAllCourses(): List<Course>{
        return getAllCoursesUseCase()
    }

    suspend fun getSortedCourses(): List<Course>{
        return getSortedCoursesUseCase()
    }

    suspend fun getReverseSortedCourses(): List<Course>{
        return getReverseSortedCoursesUseCase()
    }

    suspend fun setFavorite(id: Int, hasLike: Boolean){
        return setFavoriteUseCase(id, hasLike)
    }
}