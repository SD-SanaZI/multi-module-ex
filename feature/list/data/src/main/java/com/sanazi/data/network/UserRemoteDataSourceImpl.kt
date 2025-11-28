package com.sanazi.list.data.net

import com.sanazi.list.domain.Course
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor() : UserRemoteDataSource {
    private var cache: List<Course>? = null
    override suspend fun getCourses(): List<Course> {
        cache?.let { return it }
        val offerApi = RetrofitHelper.getInstance().create(OfferApi::class.java)
        val courses = mutableListOf<Course>()
        try {
            offerApi.getCourses().body()?.courses?.let { courses.addAll(it) }
        } catch (_: Exception) {
        }
        return courses.also { cache = it }
    }
}