package com.sanazi.em.presentation.bar

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.list.domain.Course
import com.sanazi.list.domain.UserRepository
import com.sanazi.list.presentation.CustomAdapterHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val repo: UserRepository,
) : ViewModel() {
    val adapterHelper = CustomAdapterHelper(repo)
    private var isAscending = false

    suspend fun update(listFilter: (List<Course>) -> List<Course>) {
        withContext(Dispatchers.IO) {
            adapterHelper.dataSet.addAll(listFilter(repo.update()))
        }
    }

    fun sort(){
        adapterHelper.dataSet.sortBy { it.publishDate }
        if (isAscending) adapterHelper.dataSet.reverse()
        isAscending = !isAscending
    }

    companion object {
        fun provideFactory(
            repo: UserRepository,
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