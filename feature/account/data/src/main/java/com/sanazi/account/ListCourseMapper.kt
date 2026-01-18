package com.sanazi.account

import com.sanazi.network.Course
import java.text.SimpleDateFormat
import java.util.Locale

object AccountCourseMapper{
    fun mapFromCourse(course: Course): AccountCourse {
        return AccountCourse(
            course.id,
            course.title,
            course.rate,
            formatDate(course.startDate),
            course.hasLike,
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