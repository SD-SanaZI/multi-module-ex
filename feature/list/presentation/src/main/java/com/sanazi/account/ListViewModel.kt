package com.sanazi.em.presentation.bar

import androidx.lifecycle.ViewModel
import com.sanazi.list.domain.ListCourse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ListViewModel @Inject constructor() : ViewModel() {
    val dataSet: MutableList<ListCourse> = mutableListOf()

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
}

enum class SortState{
    Standard, Sort, Reverse
}