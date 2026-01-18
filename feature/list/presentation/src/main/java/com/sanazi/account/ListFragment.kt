package com.sanazi.list.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanazi.account.ListComponent
import com.sanazi.common_ui.getAppDependenciesProvider
import com.sanazi.em.presentation.bar.ListViewModel
import com.sanazi.list.domain.GetAllCoursesUseCase
import com.sanazi.list.domain.GetReverseSortedCoursesUseCase
import com.sanazi.list.domain.GetSortedCoursesUseCase
import com.sanazi.list.domain.ListCourse
import com.sanazi.list.domain.SetFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ListFragment : Fragment(R.layout.main) {
    private lateinit var viewModel: ListViewModel

    @Inject lateinit var getAllCoursesUseCase: GetAllCoursesUseCase
    @Inject lateinit var getSortedCoursesUseCase: GetSortedCoursesUseCase
    @Inject lateinit var getReverseSortedCoursesUseCase: GetReverseSortedCoursesUseCase
    @Inject lateinit var setFavoriteUseCase: SetFavoriteUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListComponent.create(getAppDependenciesProvider()).inject(this)
        viewModel = ViewModelProvider(
            this,
            ListViewModel.provideFactory(
                getAllCoursesUseCase,
                getSortedCoursesUseCase,
                getReverseSortedCoursesUseCase,
                setFavoriteUseCase,
                this)
        )[ListViewModel::class.java]
        val customAdapter = CustomAdapter(viewModel)
        val recyclerView: RecyclerView = view.findViewById(R.id.offerList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = customAdapter
        lifecycleScope.launch {
            viewModel.update { listFilter(it) }
            customAdapter.notifyItemRangeInserted(0, viewModel.dataSet.size)
        }
        view.findViewById<View>(R.id.sort_container).setOnClickListener {
            lifecycleScope.launch {
                viewModel.sort { listFilter(it) }
                customAdapter.notifyItemRangeChanged(0, viewModel.dataSet.size)
            }
        }
    }

    abstract fun listFilter(cours: List<ListCourse>): List<ListCourse>
}

