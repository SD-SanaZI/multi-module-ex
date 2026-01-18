package com.sanazi.em.presentation.bar

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.list.domain.GetAllCoursesUseCase
import com.sanazi.list.domain.GetReverseSortedCoursesUseCase
import com.sanazi.list.domain.GetSortedCoursesUseCase
import com.sanazi.list.domain.ListCourse
import com.sanazi.list.domain.SetFavoriteUseCase
import com.sanazi.list.presentation.CustomAdapterManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val getSortedCoursesUseCase: GetSortedCoursesUseCase,
    private val getReverseSortedCoursesUseCase: GetReverseSortedCoursesUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel(), CustomAdapterManager {
    override val dataSet: MutableList<ListCourse> = mutableListOf()

    override suspend fun onFavoriteClick(position: Int) {
        withContext(Dispatchers.IO) {
            setFavoriteUseCase(dataSet[position].id, !dataSet[position].hasLike)
        }

        val index = dataSet.indexOfFirst { it.id == dataSet[position].id }
        if (index != -1) {
            val course = dataSet[index]
            dataSet.removeAt(index)
            dataSet.add(index, course.copy(hasLike = !course.hasLike))
        }
    }

    private var isAscending = false

    suspend fun update(listFilter: (List<ListCourse>) -> List<ListCourse>) {
        withContext(Dispatchers.IO) {
            dataSet.clear()
            dataSet.addAll(listFilter(getAllCoursesUseCase()))
        }
    }

    suspend fun sort(listFilter: (List<ListCourse>) -> List<ListCourse>) {
        withContext(Dispatchers.IO) {
            dataSet.let {
                it.clear()
                it.addAll(
                    listFilter(
                        if (isAscending) getReverseSortedCoursesUseCase()
                        else getSortedCoursesUseCase()
                    )
                )
            }
            isAscending = !isAscending
        }
    }

    companion object {
        fun provideFactory(
//            repo: CoursesManager,
            getAllCoursesUseCase: GetAllCoursesUseCase,
            getSortedCoursesUseCase: GetSortedCoursesUseCase,
            getReverseSortedCoursesUseCase: GetReverseSortedCoursesUseCase,
            setFavoriteUseCase: SetFavoriteUseCase,
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
                    return ListViewModel(
                        getAllCoursesUseCase,
                        getSortedCoursesUseCase,
                        getReverseSortedCoursesUseCase,
                        setFavoriteUseCase,
                    ) as T
                }
            }
    }
}