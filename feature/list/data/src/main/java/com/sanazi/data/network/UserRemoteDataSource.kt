package com.sanazi.list.data.net

import com.sanazi.list.domain.Course

interface UserRemoteDataSource {
    suspend fun getCourses(): List<Course>
}