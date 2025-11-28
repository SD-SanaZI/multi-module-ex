package com.sanazi.list.data.net

import com.sanazi.list.domain.Courses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OfferApi {
    @GET("/uc")
    suspend fun getCourses(
        @Query("id") id: String = "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q",
        @Query("export") export: String = "download"
    ) : Response<Courses>
}