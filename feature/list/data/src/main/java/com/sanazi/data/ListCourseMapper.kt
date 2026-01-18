package com.sanazi.data

import com.sanazi.list.domain.ListCourse
import com.sanazi.network.Course

object ListCourseMapper{
    fun mapToCourse(listCourse: ListCourse): Course {
        return Course(
            listCourse.id,
            listCourse.title,
            listCourse.text,
            listCourse.price,
            listCourse.rate,
            listCourse.startDate,
            listCourse.hasLike,
            listCourse.publishDate
        )
    }

    fun mapFromCourse(course: Course): ListCourse {
        return ListCourse(
            course.id,
            course.title,
            course.text,
            course.price,
            course.rate,
            course.startDate,
            course.hasLike,
            course.publishDate
        )
    }
}