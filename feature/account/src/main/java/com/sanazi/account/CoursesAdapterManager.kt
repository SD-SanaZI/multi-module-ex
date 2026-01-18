package com.sanazi.account

interface CoursesAdapterManager{
    val dataSet: List<AccountCourse>
    suspend fun onFavoriteClick(position: Int)
}