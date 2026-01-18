package com.sanazi.network

interface UserRemoteDataSource {
    suspend fun getCourses(): List<Course>
}