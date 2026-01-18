package com.sanazi.dependencies

import com.sanazi.list.domain.GetAllCoursesUseCase
import com.sanazi.list.domain.GetReverseSortedCoursesUseCase
import com.sanazi.list.domain.GetSortedCoursesUseCase
import com.sanazi.list.domain.SetFavoriteUseCase

interface CoursesManagerProvider{
    fun getAllCoursesUseCase(): GetAllCoursesUseCase
    fun getSortedCoursesUseCase(): GetSortedCoursesUseCase
    fun getReverseSortedCoursesUseCase(): GetReverseSortedCoursesUseCase
    fun setFavoriteUseCase(): SetFavoriteUseCase
}

