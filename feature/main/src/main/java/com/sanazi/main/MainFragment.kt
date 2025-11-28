package com.sanazi.main

import com.sanazi.list.domain.Course
import com.sanazi.list.presentation.ListFragment

class MainFragment : ListFragment(){
    override fun listFilter(courses: List<Course>): List<Course> {
        return courses
    }
}