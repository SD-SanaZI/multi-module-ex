package com.sanazi.account

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel(), CoursesAdapterManager {
    override val dataSet: MutableList<AccountCourse> = mutableListOf()

    override suspend fun onFavoriteClick(position: Int) {
        withContext(Dispatchers.IO) {
            setFavoriteUseCase(dataSet[position].id, !dataSet[position].hasLike)
        }
    }

    fun setData(list: List<AccountCourse>){
        dataSet.let { set->
            set.clear()
            set.addAll(
                list
            )
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
                    return AccountViewModel(
                        setFavoriteUseCase
                    ) as T
                }
            }
    }
}