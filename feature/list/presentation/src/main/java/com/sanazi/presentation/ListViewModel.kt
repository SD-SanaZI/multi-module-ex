package com.sanazi.em.presentation.bar

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.CoursesManager
import com.sanazi.list.presentation.CustomAdapterManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val manager: CoursesManager
) : ViewModel(), CustomAdapterManager {
    override val dataSet: MutableList<Course> = mutableListOf()

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

    private var isAscending = false

    suspend fun update(listFilter: (List<Course>) -> List<Course>) {
        withContext(Dispatchers.IO) {
            dataSet.clear()
            dataSet.addAll(listFilter(manager.getAllCourses()))
        }
    }

    suspend fun sort(listFilter: (List<Course>) -> List<Course>) {
        withContext(Dispatchers.IO) {
            dataSet.let {
                it.clear()
                it.addAll(
                    listFilter(
                        if (isAscending) manager.getReverseSortedCourses()
                        else manager.getSortedCourses()
                    )
                )
            }
            isAscending = !isAscending
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
                    return ListViewModel(repo) as T
                }
            }
    }
}