package com.sanazi.account

import com.sanazi.network.Course

object AccountCourseMapper{
    fun mapFromCourse(course: Course): AccountCourse {
        return AccountCourse(
            course.id,
            course.title,
            course.text,
            course.startDate,
            course.hasLike,
        )
    }
}