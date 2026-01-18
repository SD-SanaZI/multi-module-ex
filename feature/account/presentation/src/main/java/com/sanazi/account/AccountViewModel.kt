package com.sanazi.account

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.list.domain.GetAllCoursesUseCase
import com.sanazi.list.domain.ListCourse
import com.sanazi.list.domain.SetFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val getAllCoursesUseCase: GetAllCoursesUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel(), CoursesAdapterManager {
    override val dataSet: MutableList<AccountCourse> = mutableListOf()

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

    suspend fun update(listFilter: (List<ListCourse>) -> List<ListCourse>) {
        withContext(Dispatchers.IO) {
            dataSet.let {
                it.clear()
                it.addAll(
                    listFilter(getAllCoursesUseCase())
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
            getAllCoursesUseCase: GetAllCoursesUseCase,
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
                    return AccountViewModel(
                        getAllCoursesUseCase,
                        setFavoriteUseCase
                    ) as T
                }
            }
    }
}