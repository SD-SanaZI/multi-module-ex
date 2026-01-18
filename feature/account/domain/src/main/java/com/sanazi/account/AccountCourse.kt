package com.sanazi.account

import javax.inject.Inject

data class AccountCourse(
    val id: Int,
    val title: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val doneLessons: Int = 10,
    val totalLessons: Int = 52
)

interface AccountCoursesRepository{
    suspend fun getAllCourses(): List<AccountCourse>
    suspend fun setFavorite(id: Int, hasLike: Boolean)
}

class GetAllCoursesUseCase @Inject constructor(
    private val repository: AccountCoursesRepository
) {
    suspend operator fun invoke(): List<AccountCourse> {
        return repository.getAllCourses()
    }
}

class SetFavoriteUseCase @Inject constructor(
    private val repository: AccountCoursesRepository
) {
    suspend operator fun invoke(id: Int, hasLike: Boolean) {
        return repository.setFavorite(id, hasLike)
    }
}