package com.sanazi.data

import com.sanazi.list.domain.ListCourse
import com.sanazi.network.Course
import java.text.SimpleDateFormat
import java.util.Locale

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
            formatDate(course.startDate),
            course.hasLike,
            course.publishDate
        )
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: return dateString)
        } catch (e: Exception) {
            dateString
        }
    }
}