package com.sanazi.account

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.CoursesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val manager: CoursesManager
) : ViewModel(), CoursesAdapterManager {
    override val dataSet: MutableList<AccountCourse> = mutableListOf()

    override suspend fun onFavoriteClick(position: Int) {
        withContext(Dispatchers.IO) {
            manager.setFavorite(dataSet[position].id, !dataSet[position].hasLike)
        }

        val index = dataSet.indexOfFirst { it.id == dataSet[position].id }
        if (index != -1) {
            val course = dataSet[index]
            dataSet.removeAt(index)
            dataSet.add(index, course.copy(hasLike = !course.hasLike))
        }
    }

    suspend fun update(listFilter: (List<Course>) -> List<Course>) {
        withContext(Dispatchers.IO) {
            dataSet.let {
                it.clear()
                it.addAll(
                    listFilter(manager.getAllCourses())
                        .map { course ->
                            AccountCourse(
                                course.id,
                                course.title,
                                course.rate,
                                course.startDate,
                                course.hasLike
                            )
                        }
                )
            }
        }
    }

    companion object {
        fun provideFactory(
            repo: CoursesManager,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return AccountViewModel(repo) as T
                }
            }
    }
}