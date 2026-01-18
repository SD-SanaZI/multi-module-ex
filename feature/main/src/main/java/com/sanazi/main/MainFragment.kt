package com.sanazi.main

import com.sanazi.list.domain.ListCourse
import com.sanazi.list.presentation.ListFragment

class MainFragment : ListFragment(){
    override fun listFilter(cours: List<ListCourse>): List<ListCourse> {
        return cours
    }
}