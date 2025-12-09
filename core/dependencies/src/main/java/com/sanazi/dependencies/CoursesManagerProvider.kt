package com.sanazi.dependencies

import com.sanazi.list.domain.CoursesManager

interface CoursesManagerProvider{
    fun provideCoursesManager() : CoursesManager
}

