package com.sanazi.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanazi.common_ui.getAppDependenciesProvider
import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.em.presentation.bar.ListViewModel
import com.sanazi.em.presentation.bar.SortState
import com.sanazi.list.domain.GetAllCoursesUseCase
import com.sanazi.list.domain.ListCourse
import com.sanazi.list.domain.SetFavoriteUseCase
import com.sanazi.list.presentation.CustomAdapter
import dagger.Component
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.favorite_layout){
    private lateinit var viewModel: ListViewModel

    @Inject lateinit var getAllCoursesUseCase: GetAllCoursesUseCase
    @Inject lateinit var setFavoriteUseCase: SetFavoriteUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FavoriteComponent.create(getAppDependenciesProvider()).inject(this)
        viewModel = ViewModelProvider(
            this,
            ListViewModel.provideFactory(
                setFavoriteUseCase, this)
        )[ListViewModel::class.java]
        val customAdapter = CustomAdapter(viewModel)
        val recyclerView: RecyclerView = view.findViewById(R.id.offerList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = customAdapter
        lifecycleScope.launch {
            getAllCoursesUseCase()
                .combine(viewModel.isAscending){list, state -> list}
                .collect {
                    if (viewModel.isAscending.value == SortState.Standard) {
                        viewModel.setData(listFilter(it))
                        customAdapter.notifyDataSetChanged()
                    }
                }
        }
    }

    fun listFilter(cours: List<ListCourse>): List<ListCourse>{
        return cours.filter { it.hasLike }
    }
}

@Component(
    dependencies = [CoursesManagerProvider::class]
)
interface FavoriteComponent{
    fun inject(favoriteFragment: FavoriteFragment)

    companion object{
        fun create(coursesManagerProvider: CoursesManagerProvider): FavoriteComponent{
            return DaggerFavoriteComponent.factory().create(coursesManagerProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider
        ): FavoriteComponent
    }
}