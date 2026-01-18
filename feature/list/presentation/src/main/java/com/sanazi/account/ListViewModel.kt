package com.sanazi.em.presentation.bar

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sanazi.list.domain.ListCourse
import com.sanazi.list.domain.SetFavoriteUseCase
import com.sanazi.list.presentation.CustomAdapterManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel(), CustomAdapterManager {
    override val dataSet: MutableList<ListCourse> = mutableListOf()

    override suspend fun onFavoriteClick(position: Int) {
        withContext(Dispatchers.IO) {
            setFavoriteUseCase(dataSet[position].id, !dataSet[position].hasLike)
        }
    }

    val isAscending: MutableStateFlow<SortState> = MutableStateFlow(SortState.Standard)

    fun setData(list: List<ListCourse>){
        dataSet.let { set->
            set.clear()
            set.addAll(
                list
            )
        }
    }

    fun sort() {
        isAscending.value = when(isAscending.value){
            SortState.Sort -> SortState.Reverse
            else -> SortState.Sort
        }
    }

    companion object {
        fun provideFactory(
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
                        setFavoriteUseCase,
                    ) as T
                }
            }
    }
}

enum class SortState{
    Standard, Sort, Reverse
}