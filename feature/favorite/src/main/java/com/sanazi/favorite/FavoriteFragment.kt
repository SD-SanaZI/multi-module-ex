package com.sanazi.favorite

import com.sanazi.list.domain.Course
import com.sanazi.list.presentation.ListFragment

class FavoriteFragment: ListFragment(){
    override fun listFilter(courses: List<Course>): List<Course> {
        return courses.filter { it.hasLike }
    }
}